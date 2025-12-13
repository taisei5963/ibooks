package jp.blue_dolphin.ibooks.common.constant;

import lombok.Getter;

/**
 * ON・OFF用フラグ
 */
@Getter
public enum Flag implements MapValue {
    ON("1", "表示"),
    OFF("0", "非表示");

    private final String value;
    private final String description;

    Flag(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 引数の値の Flag の列挙型を取得する
     *
     * @param value 値
     * @return フラグ
     */
    public static Flag getEnum(String value) {
        return getEnum(value, null);
    }

    /**
     * 引数の値の Flag の列挙型を取得する
     *
     * @param value        値
     * @param defaultValue デフォルト値
     * @return Flag 列挙型
     */
    public static Flag getEnum(String value, Flag defaultValue) {
        for (Flag e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return defaultValue;
    }

    @Override
    public String toString() {
        return this.value + "," + super.toString();
    }
}
