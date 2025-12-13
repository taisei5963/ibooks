package jp.blue_dolphin.ibooks.common.exception;

/**
 * ダウンロード対象のファイルが見つからないときに発生する例外
 */
public class DownloadFileNotFoundException extends RuntimeException {
    private static final String message = "ダウンロード対象にファイルが見つかりません。";

    /**
     * コンストラクタ
     */
    public DownloadFileNotFoundException() {
        super(message);
    }
}
