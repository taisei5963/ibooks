package jp.blue_dolphin.ibooks.admin.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * カテゴリアップロード設定
 */
@Component
@Getter
public class CategoryUploadConfig {
    /** 文字エンコード */
    @Value("${admin-config.csv-upload.category.encode}")
    private String encode;

    /** CSVファイルアップロードパス */
    @Value("${admin-config.csv-upload.category.temp-file-name}")
    private String tempFileName;
}
