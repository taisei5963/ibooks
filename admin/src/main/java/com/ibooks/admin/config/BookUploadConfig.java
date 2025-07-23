package com.ibooks.admin.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ブックアップロード設定
 */
@Component
@Getter
public class BookUploadConfig {
    /** 文字エンコード */
    @Value("${admin-config.csv-upload.encode}")
    private String encode;
    /** CSVファイルアップロードパス */
    @Value("${admin-config.csv-upload.temp-file-name}")
    private String tmpFileName;
}
