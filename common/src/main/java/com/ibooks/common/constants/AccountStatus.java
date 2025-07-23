package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * アカウント種別
 */
@Getter
@AllArgsConstructor
public enum AccountStatus implements MapValue{
    ACTIVE("1","アクティブ"),
    FREEZE("-1", "凍結"),
    WITHDRAWN("0", "退会済");

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;

    /**
     * 引数の値のユーザー種別を取得する
     * @param value 値
     * @return ユーザー種別
     */
    public static AccountStatus getEnum(String value) {
        for (AccountStatus e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
