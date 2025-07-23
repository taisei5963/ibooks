package com.ibooks.common.exception;

import com.ibooks.common.constants.ErrorCode;

import java.io.Serial;

/**
 * 暗号復号処理が失敗したときに発生する例外
 */
public class CrypterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4248643900466192208L;

    private static final String message = "暗号復号処理に失敗しました。";

    /**
     * コンストラクタ
     */
    public CrypterException() {
        super(message);
    }

    /**
     * コンストラクタ
     *
     * @param ex 例外オブジェクト
     */
    public CrypterException(Throwable ex) {
        super(message, ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  例外オブジェクト
     */
    public CrypterException(String msg, Throwable ex) {
        super(message + msg, ex);
    }
}
