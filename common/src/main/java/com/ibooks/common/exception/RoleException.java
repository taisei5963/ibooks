package com.ibooks.common.exception;

import java.io.Serial;

/**
 * 機能を実行するための権限を所持していないときに発生する例外
 */
public class RoleException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4010853423288356335L;

    /**
     * コンストラクタ
     */
    public RoleException() {
        super();
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     */
    public RoleException(String msg) {
        super(msg);
    }

    /**
     * コンストラクタ
     *
     * @param ex 例外オブジェクト
     */
    public RoleException(Throwable ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  例外オブジェクト
     */
    public RoleException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
