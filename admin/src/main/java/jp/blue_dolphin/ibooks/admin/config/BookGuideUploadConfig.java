package jp.blue_dolphin.ibooks.admin.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ブックガイドアップロード設定
 */
@Component
@Getter
public class BookGuideUploadConfig {
    /** 文字エンコード */
    @Value("${admin-config.csv-upload.bookGuide.encode}")
    private String encode;

    /** CSVファイルアップロードパス */
    @Value("${admin-config.csv-upload.bookGuide.temp-file-name}")
    private String tempFileName;
}
