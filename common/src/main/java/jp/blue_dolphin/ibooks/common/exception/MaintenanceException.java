package jp.blue_dolphin.ibooks.common.exception;

import java.io.Serial;

public class MaintenanceException extends RuntimeException {
    /** シリアルバージョン */
    @Serial
    private static final long serialVersionUID = -6120696556394791435L;

    /** メッセージ */
    private final static String ERROR_MSG = "メンテナンス中です。";

    /** メンテナンスメッセージ */
    private final String maintenanceMsg;

    /**
     * コンストラクタ
     *
     * @param msg 表示するメッセージ
     */
    public MaintenanceException(String msg) {
        super(ERROR_MSG);
        this.maintenanceMsg = msg;
    }

    /**
     * コンストラクタ
     *
     * @param msg 表示するメッセージ
     * @param ex  例外エラー
     */
    public MaintenanceException(String msg, Throwable ex) {
        super(ERROR_MSG, ex);
        this.maintenanceMsg = msg;
    }

    /**
     * メンテナンスメッセージを返却する
     *
     * @return メンテナンスメッセージ
     */
    public String getMaintenanceMsg() {
        return this.maintenanceMsg;
    }
}
