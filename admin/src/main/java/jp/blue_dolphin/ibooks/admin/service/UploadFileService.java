package jp.blue_dolphin.ibooks.admin.service;

import jp.blue_dolphin.ibooks.common.constant.UploadFileType;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.service.UploadFileCommonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * ファイルアップロードサービス
 */
@AllArgsConstructor
@Service
public class UploadFileService {
    /** ファイルアップロード共通サービス */
    private UploadFileCommonService uploadFileCommonService;

    /**
     * 一時ファイル名を取得する
     *
     * @param tmpFileName 一時ファイルテンプレート
     * @param type        アップロードファイル種別
     * @param adminId     管理者ID
     * @return 一時ファイル
     */
    public static Path getTmpFilePath(String tmpFileName, UploadFileType type, Long adminId) {
        return UploadFileCommonService.getTmpFilePath(tmpFileName, type, adminId);
    }

    /**
     * 一時ファイル名を取得する
     *
     * @param tmpFileName 一時ファイルテンプレート
     * @param adminId     管理者ID
     * @return 一時ファイル
     */
    public static Path getTmpFilePath(String tmpFileName, Long adminId) {
        return UploadFileCommonService.getTmpFilePath(tmpFileName, adminId);
    }

    /**
     * アップロードされたCSVファイルを一時ファイルに保存する
     *
     * @param file    アップロードされたファイル
     * @param tmpFile 一時ファイル
     * @return 一時ファイル
     */
    public static Path saveTmpCsvFile(MultipartFile file, Path tmpFile) {
        return UploadFileCommonService.saveTempCsvFile(file, tmpFile);
    }

    /**
     * 引数のファイルをCSVデータとして読み込む
     *
     * @param cls      CSVデータクラス
     * @param filePath ファイルパス
     * @param encode   文字エンコード
     * @return CSVデータDTO
     * @throws IOException 処理中に例外が発生した場合
     */
    public <T> CsvDto<T> readCsv(Class<T> cls, Path filePath, String encode) throws IOException {
        return uploadFileCommonService.readCsv(cls, filePath, encode);
    }
}
