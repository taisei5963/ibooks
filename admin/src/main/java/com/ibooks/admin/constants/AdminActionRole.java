package com.ibooks.admin.constants;

import com.ibooks.common.constants.MapValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理サイト アクション権限
 */
@AllArgsConstructor
@Getter
public enum AdminActionRole implements MapValue {
    /** ブック管理 */
    BOOK("book", "ブック管理", new String[] {"book", "bookUpload"});

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;
    /** アクション */
    private final String[] actions;

    /**
     * 引数の値のアクション権限を取得する
     *
     * @param value 値
     * @return アクション権限
     */
    public static AdminActionRole getEnum(String value) {
        for (AdminActionRole e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
