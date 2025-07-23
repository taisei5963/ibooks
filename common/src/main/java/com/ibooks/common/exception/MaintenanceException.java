package com.ibooks.common.exception;

import java.io.Serial;

/**
 * メンテナンス中に発生する例外
 */
public class MaintenanceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8587026845523935595L;

    private static final String message = "メンテナンス中です。";
    private final String maintenanceMessage;

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     */
    public MaintenanceException(String msg) {
        super(message);
        this.maintenanceMessage = msg;
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  例外オブジェクト
     */
    public MaintenanceException(String msg, Throwable ex) {
        super(message, ex);
        this.maintenanceMessage = msg;
    }

    /**
     * メンテナンスメッセージを返却する
     *
     * @return メンテナンスメッセージ
     */
    public String getMaintenanceMessage() {
        return this.maintenanceMessage;
    }
}
