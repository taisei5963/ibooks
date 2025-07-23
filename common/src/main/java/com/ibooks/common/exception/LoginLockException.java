package com.ibooks.common.exception;

import org.apache.commons.jexl3.JexlException;

import java.io.Serial;

/**
 * ログインロックが発生したときの例外
 */
public class LoginLockException extends Exception {

    @Serial
    private static final long serialVersionUID = 836039025340981568L;

    private static final String message1 = "ログイン試行回数が上限に達しました。";
    private static final String message2 = "分後に再度お試しください。";

    /**
     * コンストラクタ
     */
    public LoginLockException() {
        super(message1);
    }

    /**
     * コンストラクタ
     * @param lockSec ロック時間（秒）
     */
    public LoginLockException(long lockSec) {
        super(message1 + convertMin(lockSec) + message2);
    }

    /**
     * コンストラクタ
     * @param ex 例外オブジェクト
     */
    public LoginLockException(Throwable ex) {
        super(message1, ex);
    }

    /**
     * コンストラクタ
     * @param lockSec ロック時間（秒）
     * @param ex 例外オブジェクト
     */
    public LoginLockException(long lockSec, Throwable ex) {
        super(message1 + convertMin(lockSec) + message2, ex);
    }

    /**
     * 秒を分に変換する
     * @param sec 秒
     * @return 分
     */
    private static long convertMin(long sec) {
        return sec / 60 + 1;
    }
}
