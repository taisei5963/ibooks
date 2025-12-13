package jp.blue_dolphin.ibooks.admin.config;

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
    @Value("${admin-config.csv-upload.book.encode}")
    private String encode;

    /** CSVファイルアップロードパス */
    @Value("${admin-config.csv-upload.book.temp-file-name}")
    private String tempFileName;
}
