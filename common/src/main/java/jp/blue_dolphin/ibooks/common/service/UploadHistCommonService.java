package jp.blue_dolphin.ibooks.common.service;

import jakarta.servlet.http.HttpServletResponse;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.constant.UploadStatus;
import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.database.repository.UploadHistRepository;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.model.UploadHistModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * アップロード履歴共通サービス
 */
@AllArgsConstructor
@Service
public class UploadHistCommonService {
    /** ログ */
    private static final Logger logger = LoggerFactory.getLogger(UploadHistCommonService.class);

    /** アップロード履歴リポジトリ */
    private UploadHistRepository uploadHistRepository;

    /**
     * 指定の値より登録日付が前のアップロードデータを取得する
     *
     * @param storageTerm 保管期間
     * @return アップロード履歴モデルリスト
     */
    public List<UploadHistModel> selectBeforeDayDate(LocalDateTime storageTerm) {
        return uploadHistRepository.selectBeforeDayDate(storageTerm);
    }

    /**
     * アップロード履歴を物理削除する
     *
     * @param uploadHistIds 削除対象アップロード履歴IDリスト
     */
    public void deletePhysical(List<Long> uploadHistIds) {
        uploadHistRepository.deletePhysical(uploadHistIds);
    }

    /**
     * アップロード履歴を登録
     *
     * @param uploadType アップロード種別
     * @param siteType   サイト種別
     * @param createdId  登録者ID
     * @return アップロード履歴モデル
     */
    public UploadHistModel insert(UploadType uploadType, SiteType siteType, String createdId) {
        UploadHistModel model =
                UploadHistModel.builder().uploadType(uploadType).uploadStatus(UploadStatus.EXECUTE)
                        .siteType(siteType).build();
        return uploadHistRepository.store(model, createdId);
    }

    /**
     * アップロード履歴のステータスを更新する
     *
     * @param model     アップロード履歴モデル
     * @param csvDto    CSVデータ
     * @param status    アップロードステータス
     * @param createdId 登録者ID
     * @return アップロード履歴モデル
     */
    public <Z> UploadHistModel update(UploadHistModel model, CsvDto<Z> csvDto, UploadStatus status,
                                      String createdId) {
        UploadHistModel setModel = model.toBuilder()
                .uploadStatus(status)
                .filePath(csvDto != null ? csvDto.getFilePath() : "")
                .rowCount(csvDto != null ? csvDto.getRowCount() : 0)
                .importCount(csvDto != null ? csvDto.getImportCount() : 0)
                .fileSize(csvDto != null ? csvDto.fileSize() : 0)
                .build();
        return uploadHistRepository.store(setModel, createdId);
    }

    /**
     * 引数のアップロード種別の最新のレコードを取得する
     *
     * @param uploadType アップロード種別
     * @return アップロード履歴モデル
     */
    public Optional<UploadHistModel> selectLatest(UploadType uploadType) {
        return uploadHistRepository.selectLatestByUploadType(uploadType);
    }

    /**
     * アップロード履歴のファイルをダウンロードする
     *
     * @param model    アップロード履歴モデル
     * @param response HTTPレスポンス
     */
    public void downloadCsv(UploadHistModel model, HttpServletResponse response) {
        String[] fileNames = model.getFilePath().split(",");
        if (fileNames.length == 1) {
            File file = new File(fileNames[0]);
            try (OutputStream os = response.getOutputStream()) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                response.setContentLength((int) Files.size(file.toPath()));
                os.write(Files.readAllBytes(file.toPath()));
                os.flush();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            // INFO: 複数ファイルの場合は ZIP に圧縮してダウンロード
            response.setContentType("Content-type: text/zip");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + model.getUploadType().name() + ".zip");
            try (ZipOutputStream zos = new ZipOutputStream(
                    new BufferedOutputStream(response.getOutputStream()))) {
                for (String fileName : fileNames) {
                    File file = new File(fileName);
                    if (!file.exists()) {
                        continue;
                    }
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    zos.write(Files.readAllBytes(file.toPath()));
                    zos.closeEntry();
                }
                zos.flush();
            } catch (IOException e) {
                logger.error("ZIPファイルの作成時にエラーが発生しました。" + e.getMessage(), e);
            }
        }
    }
}
