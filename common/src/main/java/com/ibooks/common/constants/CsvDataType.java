package com.ibooks.common.constants;

/**
 * CSVでデータの更新を行う際のデータ区分
 */
public enum CsvDataType implements MapValue {
    ADD("A", "追加"),
    UPDATE("U", "更新"),
    DELETE("D", "削除");

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;

    /**
     * コンストラクタ
     *
     * @param value       値
     * @param description 説明
     */
    CsvDataType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 引数の値のデータ区分を取得する
     *
     * @param value 値
     * @return データ区分
     */
    public static CsvDataType getEnum(String value) {
        for (CsvDataType e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
