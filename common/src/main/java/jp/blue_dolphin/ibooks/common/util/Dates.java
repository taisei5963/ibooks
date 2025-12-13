package jp.blue_dolphin.ibooks.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * 日付ユーティリティー
 */
public class Dates {
    /**
     * 開始と終了のバリデーションを行う<br>
     * 開始が終了よりも未来日の場合は false を返却する
     *
     * @param from 開始日
     * @param to   終了日
     * @return {@code true} OK
     */
    public static boolean validateFromTo(LocalDate from, LocalDate to) {
        if (Objects.isNull(from) || Objects.isNull(to)) {
            return true;
        }
        return validateFromTo(from.atTime(LocalTime.MIN), to.atTime(LocalTime.MAX));
    }

    /**
     * 開始と終了のバリデーションを行う<br>
     * 開始が終了よりも未来の場合は false を返却する
     *
     * @param from 開始日
     * @param to   終了日
     * @return {@code true} OK
     */
    public static boolean validateFromTo(LocalDateTime from, LocalDateTime to) {
        if (Objects.isNull(from) || Objects.isNull(to)) {
            return true;
        }
        return !from.isAfter(to);
    }

    /**
     * 日付をフォーマットする<br>
     * null の場合は空文字を返却する
     *
     * @param date 日付
     * @return フォーマット後の日付
     */
    public static String format(LocalDate date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 日付をフォーマットする<br>
     * null の場合は空文字を返却する
     *
     * @param date    日付
     * @param pattern パターン
     * @return フォーマット後の日付
     */
    public static String format(LocalDate date, String pattern) {
        if (Objects.isNull(date)) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日時をフォーマットする<br>
     * null の場合は空文字を返却する
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
     * @param pattern  パターン
     * @return フォーマット後の日時文字列
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 引数の日付文字列を LocalDate に変換する<br>
     * 日付文字列やパターンが不正な場合は null を返却する
     *
     * @param strDate 日付文字列
     * @param pattern パターン
     * @return 日付
     */
    public static LocalDate parseDate(String strDate, String pattern) {
        if (Strings.isEmpty(strDate) || Strings.isEmpty(pattern)) {
            return null;
        }
        try {
            return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * 引数の日付文字列を日時（00:00:00）に変換します<br>
     * 日付文字列やパターンが不正な場合は null を返却する
     *
     * @param strDate 日付文字列
     * @param pattern パターン
     * @return 日時（00:00:00）
     */
    public static LocalDateTime parseDateTimeFirst(String strDate, String pattern) {
        LocalDate ld = parseDate(strDate, pattern);
        if (Objects.isNull(ld)) {
            return null;
        }
        return ld.atTime(LocalTime.of(0, 0, 0));
    }

    /**
     * 引数の日付文字列を日時（23:59:59）に変換する<br>
     * 日付文字列やパターンが不正な場合は null を返却する
     *
     * @param strDate 日付文字列
     * @param pattern パターン
     * @return 日時（23:59:59）
     */
    public static LocalDateTime parseDateTImeLast(String strDate, String pattern) {
        LocalDate ld = parseDate(strDate, pattern);
        if (Objects.isNull(ld)) {
            return null;
        }
        return ld.atTime(LocalTime.of(23, 59, 59));
    }
}
