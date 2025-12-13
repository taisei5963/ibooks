package jp.blue_dolphin.ibooks.common.exception;

import jp.blue_dolphin.ibooks.common.constant.ErrorCode;

import java.io.Serial;

/**
 * システム例外エラー
 */
public class SystemException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8359793432986357348L;

    /** 本例外を分類するエラーコード */
    private ErrorCode errorCode = ErrorCode.SYSTEM_ERROR;

    /**
     * コンストラクタ
     */
    public SystemException() {
        super();
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     */
    public SystemException(final String msg) {
        super(msg);
    }

    /**
     * コンストラクタ
     *
     * @param ex この例外の原因となった例外オブジェクト
     */
    public SystemException(final Throwable ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  この例外の原因となった例外オブジェクト
     */
    public SystemException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

    /**
     * コンストラクタ
     *
     * @param errorCode エラーコード
     */
    public SystemException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * コンストラクタ
     *
     * @param errorCode エラーコード
     * @param msg       エラーメッセージ
     */
    public SystemException(final ErrorCode errorCode, final String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    /**
     * コンストラクタ
     *
     * @param errorCode エラーコード
     * @param ex        この例外の原因となった例外オブジェクト
     */
    public SystemException(final ErrorCode errorCode, final Throwable ex) {
        super(ex);
        this.errorCode = errorCode;
    }

    /**
     * コンストラクタ
     *
     * @param errorCode エラーコード
     * @param msg       エラーメッセージ
     * @param ex        この例外の原因となった例外オブジェクト
     */
    public SystemException(final ErrorCode errorCode, final String msg, final Throwable ex) {
        super(msg, ex);
        this.errorCode = errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s\n%s", this.errorCode.toString(), super.toString());
    }
}
