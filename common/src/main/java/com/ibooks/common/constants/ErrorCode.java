package com.ibooks.common.constants;

/**
 * フレームワークが投げるシステム例外の種別を表すコード値
 */
public enum ErrorCode {
    SYSTEM_ERROR("E999", "システムエラーが発生しました");

    private final String errorCode;
    private final String errorMessage;

    /**
     * コンストラクタ
     *
     * @param errorCode    エラーコード
     * @param errorMessage エラーメッセージ
     */
    ErrorCode(final String errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * getStatusCode()で取得できる値との比較を行う<br/>
     * <ul>
     *     <li>SYSTEM_ERROR.equivalent(errorCode);</li>
     *     <li>SYSTEM_ERROR.getStatusCode().equals(errorCode);</li>
     * </ul>
     * は同じ意味です
     *
     * @param errorCode 例外の種類を表すエラーコード
     * @return {@code true} 同じ
     */
    public boolean equivalent(final String errorCode) {
        return this.errorCode.equals(errorCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s : errorCode[%s] errorMessage[%s]", this.name(), this.errorCode,
                this.errorMessage);
    }
}
