package com.ibooks.common.exception;

import java.io.Serial;

/**
 * エンティティが見つからないときに発生する例外
 */
public class EntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -797613366777809652L;

    private static final String message = "エンティティが見つかりません。";

    /**
     * コンストラクタ
     */
    public EntityNotFoundException() {
        super(message);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     */
    public EntityNotFoundException(String msg) {
        super(message + msg);
    }

    /**
     * コンストラクタ
     *
     * @param ex 例外オブジェクト
     */
    public EntityNotFoundException(Throwable ex) {
        super(message, ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  例外オブジェクト
     */
    public EntityNotFoundException(String msg, Throwable ex) {
        super(message + msg, ex);
    }
}
