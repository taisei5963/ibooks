package jp.blue_dolphin.ibooks.common.constant;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

/**
 * CSVデータ区分
 */
@Getter
public enum CsvDataType implements MapValue {
    ADD("A", "新規登録"),
    UPDATE("U", "更新"),
    DELETE("D", "削除");

    private final String value;
    private final String description;

    CsvDataType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 引数の値のCSVデータ区分を取得する
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
