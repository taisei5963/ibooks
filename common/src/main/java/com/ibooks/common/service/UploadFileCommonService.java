package com.ibooks.common.service;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanReader;
import com.ibooks.common.constants.UploadFileType;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.exception.SystemException;
import com.ibooks.common.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;

/**
 * ファイルアップロード共通サービス
 */
@AllArgsConstructor
@Service
public class UploadFileCommonService {
    /** CSV共通サービス */
    private CsvCommonService csvCommonService;

    /**
     * 一時ファイルを返却する
     *
     * @param templateFileName 一時ファイル名テンプレート
     * @param type             アップロードファイル区分
     * @param uploadId         作成者ID
     * @return 一時ファイル
     */
    public static Path getTempFilePath(String templateFileName, UploadFileType type,
                                       Long uploadId) {
        return Paths.get(MessageFormat.format(templateFileName, type.name(), uploadId,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))));
    }

    /**
     * 一時ファイルを返却する
     *
     * @param templateFileName 一時ファイル名テンプレート
     * @param uploadId         作成者ID
     * @return 一時ファイル
     */
    public static Path getTempFilePath(String templateFileName, Long uploadId) {
        return Paths.get(MessageFormat.format(templateFileName, uploadId,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))));
    }

    /**
     * アップロードされた CSV ファイルを一時ファイルに保存
     *
     * @param file     アップロードされた CSV ファイル
     * @param tempFile 一時ファイル
     * @return 一時ファイル
     */
    public static Path saveTempCsvFile(MultipartFile file, Path tempFile) {
        try {
            Files.createDirectories(tempFile.getParent());
            if (!Objects.isNull(file.getOriginalFilename()) &&
                    (file.getOriginalFilename().endsWith(".zip") ||
                            file.getOriginalFilename().endsWith(".ZIP"))) {
                Path zipFile = tempFile.getParent()
                        .resolve(tempFile.getFileName().toString().replace(".csv", ".zip"));
                Files.write(zipFile, file.getBytes());

                try {
                    ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.toFile()));
                    FileOutputStream fos = new FileOutputStream(tempFile.toFile());
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    zis.getNextEntry();
                    byte[] data = new byte[1024];
                    int count;
                    while ((count = zis.read(data)) != -1) {
                        bos.write(data, 0, count);
                    }
                } catch (Exception e) {
                    throw new SystemException(
                            "クライアントからアップロードされたファイルの保存に失敗", e);
                }
            } else {
                Files.write(tempFile, file.getBytes());
            }
        } catch (IOException e) {
            throw new SystemException("クライアントからアップロードされたファイルの保存に失敗", e);
        }
        return tempFile;
    }

    /**
     * 引数のファイルをCSVデータとして読み込む
     *
     * @param cls      CSVデータクラス
     * @param filePath ファイルパス
     * @param encode   文字エンコード
     * @param <T>      CsvBeanクラス
     * @return CSVデータDTO
     */
    public <T> CsvDto<T> readCsv(Class<T> cls, Path filePath, String encode) throws IOException {
        CsvDto<T> dto;
        try (CsvAnnotationBeanReader<T> csvReader = new CsvAnnotationBeanReader<>(
                csvCommonService.getBeanMapping(cls),
                FileUtil.newBufferedReader(filePath, Charset.forName(encode)),
                CsvPreference.STANDARD_PREFERENCE)) {
            int rowCount = 0;
            List<T> list = new ArrayList<>();
            T csv;
            while (true) {
                try {
                    csv = csvReader.read();
                    if (Objects.isNull(csv)) {
                        break;
                    }
                    list.add(csv);
                } catch (SuperCsvException e) {
                    // INFO: 善行バリデーションするためスローしない。メッセージは getErrorMessage にてまとめて取得
                } finally {
                    rowCount++;
                }
            }
            dto = new CsvDto<>(filePath, list, csvReader.getErrorMessages(), rowCount);
        }
        return dto;
    }
}
