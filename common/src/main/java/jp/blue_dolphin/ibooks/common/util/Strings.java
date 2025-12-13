package jp.blue_dolphin.ibooks.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jp.blue_dolphin.ibooks.common.exception.SystemException;
import org.apache.commons.text.StringEscapeUtils;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HexFormat;
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
    public static <T extends Number> String toString(T value) {
        if (Objects.isNull(value)) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 引数の文字列が空か null の場合は true を返却する
     *
     * @param text 文字列
     * @return {@code true} 文字列が空か null
     */
    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || text.isEmpty();
    }

    /**
     * 引数の文字列が空や空白文字か null の場合は true を返却する
     *
     * @param text 文字列
     * @return 空や空白文字か null
     */
    public static boolean isBlank(String text) {
        return Objects.isNull(text) || text.isBlank();
    }

    /**
     * 文字列配列を連結して返却する
     *
     * @param array     文字列配列
     * @param separator セパレーター
     * @return 連結した文字列
     */
    public static String join(String[] array, String separator) {
        if (Objects.isNull(array) || array.length == 0) {
            return "";
        }
        return String.join(separator, Arrays.asList(array));
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
     * 指定桁数のランダム数字文字列を生成する
     *
     * @param length 桁数
     * @return 数字文字列
     */
    public static String createNumberCode(int length) {
        if (length < 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        sb.append("0".repeat(length));
        long max = Long.parseLong(sb.toString());
        Random rand = new Random();
        return String.format("%0" + length + "d", rand.nextLong(max));
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
        byte[] bytes = messageDigest.digest(str.getBytes());
        HexFormat hex = HexFormat.of().withLowerCase();
        return hex.formatHex(bytes);
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
     * 文字列内の Windows-31J と互換性がない文字を返却する
     *
     * @param value チェック対象文字列
     * @return Windows-31J と互換性がない文字列
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
            if (c >= ' ' && c <= '~') {
                continue;
            } else if (c == '¥' || c == '‾') {
                continue;
            } else if (c >= '｡' && c <= 'ﾟ') {
                continue;
            }
            byte[] b;
            String s = Character.toString(c);
            try {
                b = s.getBytes("MS932");
                if (!s.contentEquals(new String(b, "MS932"))) {
                    return s;
                }
            } catch (UnsupportedEncodingException e) {
                return s;
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
     * 引数のメールアドレスを検証する
     *
     * @param value 検証するメールアドレス
     * @return {@code true} OK
     */
    public static boolean validateEmail(String value) {
        EmailValidator validator = new EmailValidator();
        return validator.isValid(value, null);
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
