package jp.blue_dolphin.ibooks.common.exception;

import java.io.Serial;

/**
 * ログインロックが発生したときに発生する例外
 */
public class LoginLockException extends Exception {
    @Serial
    private static final long serialVersionUID = -7978764218228632597L;

    private static final String message1 = "ログイン試行回数が上限に達しました。";
    private static final String message2 = "分後に再度ログインしてください。";

    /**
     * コンストラクタ
     */
    public LoginLockException() {
        super(message1);
    }

    /**
     * コンストラクタ
     *
     * @param lockSec ロックする秒数
     */
    public LoginLockException(long lockSec) {
        super(message1 + convertMin(lockSec) + message2);
    }

    /**
     * コンストラクタ
     *
     * @param ex この例外の原因となった例外オブジェクト
     */
    public LoginLockException(Throwable ex) {
        super(message1, ex);
    }

    /**
     * コンストラクタ
     *
     * @param lockSec ロックする秒数
     * @param ex      この例外の原因となった例外オブジェクト
     */
    public LoginLockException(long lockSec, Throwable ex) {
        super(message1 + convertMin(lockSec) + message2, ex);
    }

    /**
     * 秒を分に変換する
     *
     * @param sec 秒
     * @return 分
     */
    private static long convertMin(long sec) {
        return sec / 60 + 1;
    }
}
