package com.ibooks.admin.service;

import com.ibooks.common.constants.UploadFileType;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.service.UploadFileCommonService;
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
    /** 共通アップロードサービス */
    private UploadFileCommonService uploadFileCommonService;

    /**
     * 一時ファイル名を返却する
     *
     * @param templateFIleName 一時ファイル名テンプレート
     * @param type             アップロードファイル区分
     * @param adminId          管理者ID
     * @return 一時ファイル
     */
    public static Path getTempFilePath(String templateFIleName, UploadFileType type, Long adminId) {
        return UploadFileCommonService.getTempFilePath(templateFIleName, type, adminId);
    }

    /**
     * 一時ファイル名を返却する
     *
     * @param templateFIleName 一時ファイル名テンプレート
     * @param adminId          管理者ID
     * @return 一時ファイル
     */
    public static Path getTempFilePath(String templateFIleName, Long adminId) {
        return UploadFileCommonService.getTempFilePath(templateFIleName, adminId);
    }

    /**
     * アップロードされたCSVファイルを一時ファイルに保存する
     *
     * @param source  アップロードされたファイル
     * @param tmpFile 一時ファイル
     * @return 一時ファイル
     */
    public static Path saveTempCsvFile(MultipartFile source, Path tmpFile) {
        return UploadFileCommonService.saveTempCsvFile(source, tmpFile);
    }

    /**
     * 引数のファイルをCSVデータとして読み込む
     *
     * @param cls      CSVデータクラス
     * @param filePath ファイルパス
     * @param encode   文字エンコード
     * @return CSVデータDTO
     * @throws IOException 読み込みに失敗したとき
     */
    public <T> CsvDto<T> readCsv(Class<T> cls, Path filePath, String encode) throws IOException {
        return uploadFileCommonService.readCsv(cls, filePath, encode);
    }
}
