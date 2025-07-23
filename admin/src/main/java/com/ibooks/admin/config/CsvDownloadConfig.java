package com.ibooks.admin.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CSVダウンロード設定
 */
@Component
@Getter
public class CsvDownloadConfig {

    /** 文字エンコード */
    @Value("${admin-config.csv-download.encode}")
    private String encode;

    /** 出力先ディレクトリ */
    @Value("${admin-config.csv-download.dir}")
    private String dir;
}
