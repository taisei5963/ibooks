package com.ibooks.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * ファイルアップロード処理での例外エラー
 */
public class UploadException extends RuntimeException {

    /** アップロード詳細メッセージ */
    private String detailMessage;
    /** アップロード詳細メッセージリスト */
    private List<String> detailMessageList = new ArrayList<>();

    /**
     * コンストラクタ
     */
    public UploadException() {
        super();
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     */
    public UploadException(String msg) {
        super(msg);
    }

    /**
     * コンストラクタ
     *
     * @param ex 例外オブジェクト
     */
    public UploadException(Throwable ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg エラーメッセージ
     * @param ex  例外オブジェクト
     */
    public UploadException(String msg, Throwable ex) {
        super(msg, ex);
    }

    /**
     * コンストラクタ
     *
     * @param msg       エラーメッセージ
     * @param detailMeg 詳細メッセージ
     */
    public UploadException(final String msg, final String detailMeg) {
        super(msg);
        this.detailMessage = detailMeg;
    }

    /**
     * コンストラクタ
     *
     * @param msg           エラーメッセージ
     * @param detailMsgList 詳細メッセージリスト
     */
    public UploadException(final String msg, final List<String> detailMsgList) {
        super(msg);
        this.detailMessageList = detailMsgList;
    }

    /**
     * アップロード詳細メッセージを取得する
     *
     * @return アップロード詳細メッセージ
     */
    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * アップロード詳細メッセージリストを取得する
     *
     * @return アップロード詳細メッセージリスト
     */
    public List<String> getDetailMessageList() {
        return detailMessageList;
    }
}
