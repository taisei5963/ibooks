package jp.blue_dolphin.ibooks.admin.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ブックチャプターアップロード設定
 */
@Component
@Getter
public class BookChapterUploadConfig {
    /** 文字エンコード */
    @Value("${admin-config.csv-upload.bookChapter.encode}")
    private String encode;

    /** CSVファイルアップロードパス */
    @Value("${admin-config.csv-upload.bookChapter.temp-file-name}")
    private String tempFileName;
}
