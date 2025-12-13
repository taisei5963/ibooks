package jp.blue_dolphin.ibooks.common.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * メッセージサービス
 */
@AllArgsConstructor
@Service
public class MessageService {
    /** メッセージソース */
    private MessageSource messageSource;

    /**
     * メッセージを取得する
     *
     * @param code コード
     * @param args 引数
     * @return メッセージ
     */
    public String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

    /**
     * メッセージを取得する
     *
     * @param code コード
     * @return メッセージ
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    /**
     * リスト用のエラーメッセージを返却する
     *
     * @param rowNum  行数
     * @param code    コード
     * @param message メッセージ
     * @return エラーリスト用メッセージ
     */
    public String getErrorForList(int rowNum, String code, String message) {
        return "[" + (rowNum + 1) + "行目：" + code + "]" + message;
    }

    /**
     * リスト用のエラーメッセージを返却する
     *
     * @param rowNum  行数
     * @param message メッセージ
     * @return エラーリスト用メッセージ
     */
    public String getErrorForNoCodeList(int rowNum, String message) {
        return "[" + (rowNum + 1) + "行目：" + "]" + message;
    }
}
