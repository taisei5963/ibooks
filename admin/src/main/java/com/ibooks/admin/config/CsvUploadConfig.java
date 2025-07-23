package com.ibooks.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CSVアップロード設定
 */
@Component
@Getter
public class CsvUploadConfig {

    /** 文字エンコード */
    @Setter
    @Value("${admin-config.csv-upload.encode}")
    private String encode;

    /** CSVファイルアップロードパス */
    @Setter
    @Value("${admin-config.csv-upload.temp-file-name}")
    private String tmpFileName;
}
