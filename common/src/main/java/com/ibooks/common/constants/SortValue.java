package com.ibooks.common.constants;

/**
 * ソート順
 */
public enum SortValue {
    /** 昇順 */
    ASC("ASC"),
    DESC("DESC");

    /** 値 */
    private final String value;

    /**
     * 値を取得する
     *
     * @return 値
     */
    public String getValue() {
        return value;
    }

    /**
     * コンストラクタ
     * @param value 値
     */
    SortValue(String value) {
        this.value = value;
    }
}
