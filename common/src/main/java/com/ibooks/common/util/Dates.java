package com.ibooks.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 日付ユーティリティ
 */
public class Dates {

    /**
     * 日時をフォーマットする
     *
     * @param dateTime 日時
     * @return フォーマット後の日時文字列
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 日時をフォーマットする<br>
     * null の場合は空文字を返却する
     *
     * @param dateTime 日時
     * @param pattern  フォーマットパターン
     * @return フォーマット後の日時文字列
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
