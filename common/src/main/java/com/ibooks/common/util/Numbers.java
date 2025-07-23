package com.ibooks.common.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 数値ユーティリティ
 */
public class Numbers {

    /**
     * 引数の文字列を int 型に変換する<br>
     * 変更できない場合は 0 を返却する
     *
     * @param s 文字列
     * @return 整数
     */
    public static int toInt(String s) {
        return toInt(s, 0);
    }

    /**
     * 引数の文字列を int 型に変換する
     *
     * @param s            文字列
     * @param defaultValue 変換できない場合の値
     * @return 整数
     */
    public static int toInt(String s, int defaultValue) {
        if (Objects.isNull(s)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 引数の Integer 型を int 型に変換する<br>
     * null の場合は 0 を返却する
     *
     * @param i Integer 型
     * @return int 型
     */
    public static int toInt(Integer i) {
        if (Objects.isNull(i)) {
            return 0;
        }
        return i.intValue();
    }

    /**
     * 引数の文字列を Integer 型に変換する
     *
     * @param s            文字列
     * @param defaultValue 変換できない場合の値
     * @return 整数
     */
    public static Integer toInteger(String s, Integer defaultValue) {
        if (Strings.isEmpty(s)) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 引数の Integer 型を long 型に変換する<br>
     * null の場合は 0 を返却する
     *
     * @param i Integer 型
     * @return long 型
     */
    public static long toLong(Integer i) {
        if (Objects.isNull(i)) {
            return 0;
        }
        return i.longValue();
    }

    /**
     * 引数の文字列を BigDecimal 型に変換する
     * @param s 文字列
     * @param defaultValue 変換できない場合の値
     * @return BigDecimal
     */
    public static BigDecimal toDecimal(String s, BigDecimal defaultValue) {
        if (Objects.isNull(s)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
