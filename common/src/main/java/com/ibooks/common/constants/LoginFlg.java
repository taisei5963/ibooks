package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ログイン可否フラグ
 */
@AllArgsConstructor
@Getter
public enum LoginFlg implements MapValue {
    ON("1", "有効"),
    OFF("0", "無効");

    /**
     * 値
     */
    private final String value;
    /**
     * 説明
     */
    private final String description;

    /**
     * 引数の値のログイン可否フラグを返却する
     *
     * @param value 値
     * @return ログイン可否フラグ
     */
    public static LoginFlg getEnum(String value) {
        return getEnum(value, null);
    }

    /**
     * 引数の値のログイン可否フラグを返却する
     *
     * @param value        値
     * @param defaultValue デフォルト値
     * @return ログイン可否フラグ
     */
    public static LoginFlg getEnum(String value, LoginFlg defaultValue) {
        for (LoginFlg e : LoginFlg.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return defaultValue;
    }
}
