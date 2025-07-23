package com.ibooks.common.dto;

/**
 * 実行ログインターフェース
 */
public interface ExecLogger {

    /**
     * エラーログを記録する
     *
     * @param msg メッセージ
     */
    void error(String msg);

    /**
     * エラーログを記録する
     *
     * @param msg メッセージ
     * @param ex 例外オブジェクト
     */
    void error(String msg, Throwable ex);

    /**
     * 警告ログを記録する
     *
     * @param msg メッセージ
     */
    void warn(String msg);

    /**
     * 警告ログを記録する
     *
     * @param msg メッセージ
     * @param ex 例外オブジェクト
     */
    void warn(String msg, Throwable ex);

    /**
     * 情報ログを記録する
     *
     * @param msg メッセージ
     */
    void info(String msg);

    /**
     * 情報ログを記録する
     *
     * @param msg メッセージ
     * @param ex 例外オブジェクト
     */
    void info(String msg, Throwable ex);

    /**
     * デバッグログを記録する
     *
     * @param msg メッセージ
     */
    void debug(String msg);

}
