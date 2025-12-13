package jp.blue_dolphin.ibooks.common.exception;

import java.io.Serial;

/**
 * エンティティが見つからないときに発生する例外
 */
public class EntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1775483658240743012L;

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
     * @param msg メッセージ
     */
    public EntityNotFoundException(String msg) {
        super(message + msg);
    }

    /**
     * コンストラクタ
     *
     * @param ex この例外の原因となった例外オブジェクト
     */
    public EntityNotFoundException(Throwable ex) {
        super(message, ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg メッセージ
     * @param ex  この例外の原因となった例外オブジェクト
     */
    public EntityNotFoundException(String msg, Throwable ex) {
        super(message + msg, ex);
    }
}
