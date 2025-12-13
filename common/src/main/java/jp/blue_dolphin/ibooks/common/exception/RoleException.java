package jp.blue_dolphin.ibooks.common.exception;

import java.io.Serial;

/**
 * 機能を実行するための権限を所有していないときに発生する例外
 */
public class RoleException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8105013969825830906L;

    /**
     * コンストラクタ
     */
    public RoleException() {
        super();
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     */
    public RoleException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     *
     * @param ex この例外の原因となった例外オブジェクト
     */
    public RoleException(Throwable ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param ex      この例外の原因となった例外オブジェクト
     */
    public RoleException(String message, Throwable ex) {
        super(message, ex);
    }
}
