package com.ibooks.common.service;

import com.ibooks.common.constants.SiteType;
import com.ibooks.common.constants.UploadStatus;
import com.ibooks.common.constants.UploadType;
import com.ibooks.common.database.repository.UploadHrRepository;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.model.UploadHrModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UploadHrCommonService {

    /** ログ */
    private static final Logger log = LoggerFactory.getLogger(UploadHrCommonService.class);

    /** アップロード履歴レポジトリ */
    private UploadHrRepository uploadHrRepository;

    /**
     * アップロード履歴を物理削除
     *
     * @param uploadHrIdList 削除対象のアップロード履歴IDリスト
     */
    public void deletePhysical(List<Long> uploadHrIdList) {
        uploadHrRepository.deletePhysical(uploadHrIdList);
    }

    /**
     * アップロード履歴を登録
     *
     * @param uploadType アップロード種別
     * @param siteType   サイト種別
     * @param registerId 登録者ID
     * @return アップロード履歴モデル
     */
    public UploadHrModel insert(UploadType uploadType, SiteType siteType, String registerId) {
        UploadHrModel model =
                UploadHrModel.builder().uploadType(uploadType)
                        .uploadStatus(UploadStatus.EXECUTE)
                        .siteType(siteType).build();
        return uploadHrRepository.store(model, registerId);
    }

    /**
     * アップロード履歴を更新する
     *
     * @param model      アップロード履歴モデル
     * @param csvDto     CSVデータ
     * @param status     アップロードステータス
     * @param registerId 登録者ID
     * @return アップロード履歴モデル
     */
    public <Z> UploadHrModel update(UploadHrModel model, CsvDto<Z> csvDto,
                                    UploadStatus status, String registerId) {
        UploadHrModel setModel = model.toBuilder().uploadStatus(status)
                .filePath(csvDto != null ? csvDto.getFilePath() : "")
                .fileSize(csvDto != null ? csvDto.fileSize() : 0)
                .rowCount(csvDto != null ? csvDto.getRowCount() : 0)
                .importCount(csvDto != null ? csvDto.getImportCount() : 0)
                .build();
        return uploadHrRepository.store(setModel, registerId);
    }

    /**
     * 引数のアップロード種別の最新レコードを取得する
     *
     * @param uploadType アップロード種別
     * @return アップロード履歴モデル
     */
    public Optional<UploadHrModel> selectLatest(UploadType uploadType) {
        return uploadHrRepository.selectLatestByUploadType(uploadType);
    }
}
