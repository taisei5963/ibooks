package com.ibooks.common.exception;

/**
 * ダウンロード対象のファイルが存在しない場合に発生する例外
 */
public class DownloadFileNotFoundException extends RuntimeException {
    /** メッセージ */
    private static final String message = "ダウンロード対象のファイルが見つかりません。";

    /**
     * コンストラクタ
     */
    public DownloadFileNotFoundException() {
        super(message);
    }
}
