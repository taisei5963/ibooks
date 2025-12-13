package jp.blue_dolphin.ibooks.admin.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ブック設定
 */
@Component
public class BookConfig {
    /** CSVダウンロード設定 */
    @Autowired
    private CsvDownloadConfig csvDownloadConfig;

    /** 識別子 */
    @Getter
    @Value("${admin-config.csv-download.book.identifier}")
    private String identifier;

    /**
     * 文字エンコードを取得する
     *
     * @return 文字エンコード
     */
    public String getEncode() {
        return this.csvDownloadConfig.getEncode();
    }

    /**
     * 出力先ディレクトリを取得する
     *
     * @return 出力先ディレクトリ
     */
    public String getOutputDir() {
        return this.csvDownloadConfig.getDir();
    }
}
