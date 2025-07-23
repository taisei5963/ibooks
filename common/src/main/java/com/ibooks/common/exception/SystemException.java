package com.ibooks.common.exception;

import com.ibooks.common.constants.ErrorCode;

/**
 * 予期せぬ事象が発生したことを表す
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -3807385970481685822L;

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
     * @param ex 例外オブジェクト
     */
    public SystemException(final Throwable ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  例外オブジェクト
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
     * @param ex        例外オブジェクト
     */
    public SystemException(final ErrorCode errorCode, final Throwable ex) {
        super(ex);
        this.errorCode = errorCode;
    }

    /**
     * コンストラクタ
     *
     * @param msg       エラーメッセージ
     * @param errorCode エラーコード
     * @param ex        例外オブジェクト
     */
    public SystemException(final String msg, final ErrorCode errorCode, final Throwable ex) {
        super(msg, ex);
        this.errorCode = errorCode;
    }

    /**
     * この例外を分類するエラーコードを返す
     * @return ErrorCodeオブジェクト
     */
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s\n%s", this.errorCode.toString(), super.toString());
    }
}
