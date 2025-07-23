package com.ibooks.common.util;

import com.ibooks.common.annotation.Crypt;
import com.ibooks.common.exception.CrypterException;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * 暗号復号クラス
 */
public class Crypter {
    private static final String ALGORITHM = "Blowfish";
    private static final String MODE = "CBC";
    private static final String PADDING = "PKCS5Padding";
    private static final String HASH = "sha-256";
    private static final String CRYPT_ENCODE = "UTF-8";
    private static final String TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;

    /**
     * 暗号化
     *
     * @param str       平文文字列
     * @param cls       クラス
     * @param fieldName フィールド名
     * @return 暗号化された文字
     */
    public static String encrypt(String str, Class<?> cls, String fieldName) {
        Field field;
        try {
            field = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            return str;
        }
        Crypt crypt = field.getAnnotation(Crypt.class);
        if (Objects.isNull(crypt)) {
            return str;
        }
        if (crypt.split()) {
            return splitEncrypt(str, crypt.key(), crypt.iv());
        }
        return encrypt(str, crypt.key(), crypt.iv());
    }

    /**
     * 復号化
     *
     * @param str       暗号化された文字
     * @param cls       クラス
     * @param fieldName フィールド名
     * @return 平文文字列
     */
    public static String decrypt(String str, Class<?> cls, String fieldName) {
        Field field;
        try {
            field = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            return str;
        }
        Crypt crypt = field.getAnnotation(Crypt.class);
        if (Objects.isNull(crypt)) {
            return str;
        }
        if (crypt.split()) {
            return splitDecrypt(str, crypt.key(), crypt.iv());
        }
        return decrypt(str, crypt.key(), crypt.iv());
    }

    /**
     * 暗号化
     *
     * @param str 平文文字列
     * @param key 暗号キー文字列
     * @param iv  初期化文字列
     * @return 暗号化された文字
     */
    public static String encrypt(String str, String key, String iv) {
        if (!StringUtils.hasText(str)) {
            return str;
        }

        String encrypted;
        try {
            MessageDigest md = MessageDigest.getInstance(HASH);
            md.update(key.getBytes(CRYPT_ENCODE));
            byte[] digest = md.digest();

            SecretKeySpec secretKeySpec = new SecretKeySpec(digest, ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(CRYPT_ENCODE));

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] bytes = cipher.doFinal(str.getBytes(CRYPT_ENCODE));
            encrypted = new String(Base64.getEncoder().encode(bytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new CrypterException(e);
        }

        return encrypted;
    }

    /**
     * 復号化
     *
     * @param str 暗号化された文字
     * @param key 暗号キー文字列
     * @param iv  初期化文字列
     * @return 平文文字列
     */
    public static String decrypt(String str, String key, String iv) {
        if (!StringUtils.hasText(str)) {
            return str;
        }

        try {
            str = str.replace("-", "+");
            str = str.replace("_", "/");
            str = str.replace(".", "=");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            MessageDigest md = MessageDigest.getInstance(HASH);
            md.update(key.getBytes(CRYPT_ENCODE));
            byte[] digest = md.digest();
            ;

            SecretKeySpec keySpec = new SecretKeySpec(digest, ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(CRYPT_ENCODE));

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] ret = cipher.doFinal(Base64.getDecoder().decode(str));

            return new String(ret, CRYPT_ENCODE);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new CrypterException(e);
        }
    }

    /**
     * 暗号化<br>
     * 文字列を１文字単位で暗号化してカンマで結合する
     *
     * @param str 平文文字列
     * @param key 暗号キー文字列
     * @param iv  初期化文字列
     * @return 暗号化された文字
     */
    public static String splitEncrypt(String str, String key, String iv) {
        if (!StringUtils.hasText(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append(encrypt(Character.toString(c), key, iv));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * 復号化<br>
     * @param str 暗号化された文字
     * @param key 暗号キー文字列
     * @return 平文文字列
     */
    public static String splitDecrypt(String str, String key, String iv) {
        if (!StringUtils.hasText(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : str.split(",")) {
            sb.append(decrypt(s, key, iv));
        }

        return sb.toString();
    }
}
