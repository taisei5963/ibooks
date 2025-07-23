package com.ibooks.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibooks.common.exception.SystemException;
import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * 文字列ユーティリティー
 */
public class Strings {

    /**
     * 引数の値を文字列に変換する
     *
     * @param value 値
     * @return 文字列
     */
    public static String toString(Integer value) {
        return value != null ? String.valueOf(value) : "";
    }

    /**
     * 引数の値を文字列に変換する
     *
     * @param value 値
     * @return 文字列
     */
    public static String toString(Long value) {
        return value != null ? String.valueOf(value) : "";
    }

    /**
     * 引数の文字列が空や null の場合は true を返却する
     *
     * @param text 文字列
     * @return {@code true} 空や空白文字、null
     */
    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * 引数の文字列が空や空白文字、null の場合は true を返却する
     *
     * @param text 文字列
     * @return {@code true} 空や空白文字、null
     */
    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    /**
     * 文字列配列を連結して返却する
     *
     * @param arr       文字列配列
     * @param separator セパレーター
     * @return 連結文字列
     */
    public static String join(String[] arr, String separator) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return "";
        }
        return String.join(separator, Arrays.asList(arr));
    }

    /**
     * 引数のオブジェクトをJSON文字列に変換する
     *
     * @param object    オブジェクト
     * @param forScript {@code true} スクリプト用にエスケープする
     * @return JSON文字列
     */
    public static String toJsonString(Object object, boolean forScript) {
        if (Objects.isNull(object)) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SystemException(e);
        }
        if (forScript) {
            return StringEscapeUtils.escapeEcmaScript(json);
        }
        return json;
    }

    /**
     * 指定桁数のランダム数文字列を作成する
     *
     * @param length 桁数
     * @return 数字文字列
     */
    public static String createNumberCode(int length) {
        if (length < 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("1");
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }
        long max = Long.parseLong(sb.toString());
        Random r = new Random();
        return String.format("%0" + length + "d", r.nextLong(max));
    }

    /**
     * 引数の文字列のMD5ハッシュ値を算出する
     *
     * @param str 文字列
     * @return MD5ハッシュ値
     */
    public static String md5(String str) {
        return digest(str, "MD5");
    }

    /**
     * 引数の文字列のSHA-256ハッシュ値を算出する
     *
     * @param str 文字列
     * @return SHA-256ハッシュ値
     */
    public static String sha256(String str) {
        return digest(str, "SHA-256");
    }

    /**
     * 引数の文字列のハッシュ値を算出する
     *
     * @param str       文字列
     * @param algorithm アルゴリズム
     * @return ハッシュ値
     */
    private static String digest(String str, String algorithm) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] byts = messageDigest.digest(str.getBytes());
        HexFormat hex = HexFormat.of().withLowerCase();
        return hex.formatHex(byts);
    }

    /**
     * 引数の文字列のCRC32ハッシュ値を算出する
     *
     * @param str 文字列
     * @return CRC32ハッシュ値
     */
    public static String crc32(String str) {
        CRC32 crc = new CRC32();
        crc.update(str.getBytes());
        return Long.toHexString(crc.getValue());
    }

    /**
     * 文字列内の Windows-31J と互換性がない文字列を返却する
     *
     * @param value チェック対象文字列
     * @return Windows-31J と互換性のない文字
     */
    public static String searchIncompatibleChar(String value) {
        if (Strings.isEmpty(value)) {
            return null;
        }
        for (int i = 0, size = value.length(); i < size; i++) {
            char c = value.charAt(i);
            if (Character.isHighSurrogate(c)) {
                if ((i + 1) < size) {
                    return value.substring(i, i + 2);
                } else {
                    return "?";
                }
            }
            if (c >= '\u0020' && c <= '\u007e') {
                // INFO: 英数記号
                continue;
            } else if (c == '\u00a5' || c == '\u203e') {
                // INFO: 円マーク、チルダ
                continue;
            } else if (c >= '\uff61' && c <= '\uff9f') {
                // INFO: 半角カナ
                continue;
            }
            byte[] b;
            String str = Character.toString(c);
            try {
                b = str.getBytes("MS932");
                if (!str.contentEquals(new String(b, "MS932"))) {
                    return str;
                }
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }
        return null;
    }

    /**
     * 数値をフォーマットする
     *
     * @param i 整数
     * @return 数値文字列
     */
    public static String numberFormat(Integer i) {
        if (Objects.isNull(i)) {
            return "0";
        }
        return NumberFormat.getNumberInstance().format(i);
    }

    /**
     * 数値をフォーマットする
     *
     * @param i 整数
     * @return 数値文字列
     */
    public static String numberFormat(Long i) {
        if (Objects.isNull(i)) {
            return "0";
        }
        return NumberFormat.getNumberInstance().format(i);
    }

    /**
     * パスワードを生成する
     *
     * @param length パスワードの桁数
     * @return パスワード
     */
    public static String generatePassword(int length) {
        final String[] basePass = {"abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789"};
        List<String> list = new ArrayList<>(Arrays.asList(basePass));
        Random r = new Random();
        for (int i = basePass.length; i < length; i++) {
            int index = r.nextInt(basePass.length);
            list.add(basePass[index]);
        }
        Collections.shuffle(list);
        StringBuilder password = new StringBuilder();
        for (String base : list) {
            int index = r.nextInt(base.length());
            password.append(base.charAt(index));
        }
        return password.toString();
    }

    /**
     * 引数の文字列をURLエンコード（UTF-8）する
     *
     * @param text 文字列
     * @return URLエンコードした文字列
     */
    public static String urlEncode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}