package jp.blue_dolphin.ibooks.common.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * ファイルアップロード処理での例外エラー<br>
 * ロールバックするように RuntimeException を継承
 */
@Getter
public class UploadException extends RuntimeException {
    /** アップロード詳細メッセージ */
    private String detailMessage;
    /** アップロード詳細メッセージリスト */
    private List<String> detailMessageList = new ArrayList<>();

    /**
     * コンストラクタ
     */
    public UploadException() {
        super();
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     */
    public UploadException(final String message) {
        super(message);
    }

    /**
     * コンストラクタ
     *
     * @param ex この例外の原因となった例外オブジェクト
     */
    public UploadException(final Throwable ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param ex      この例外の原因となった例外オブジェクト
     */
    public UploadException(final String message, final Throwable ex) {
        super(message, ex);
    }

    /**
     * コンストラクタ
     *
     * @param message       メッセージ
     * @param detailMessage 詳細メッセージ
     */
    public UploadException(final String message, final String detailMessage) {
        super(message);
        this.detailMessage = detailMessage;
    }

    /**
     * コンストラクタ
     *
     * @param message           メッセージ
     * @param detailMessageList 詳細メッセージリスト
     */
    public UploadException(final String message, final List<String> detailMessageList) {
        super(message);
        this.detailMessageList = detailMessageList;
    }
}
