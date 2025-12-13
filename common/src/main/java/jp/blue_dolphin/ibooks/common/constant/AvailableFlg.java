package jp.blue_dolphin.ibooks.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 利用可否フラグ
 */
@AllArgsConstructor
@Getter
public enum AvailableFlg implements MapValue {
    ON("1", "利用可"),
    OFF("0", "利用不可");

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;

    /**
     * 引数の値の利用可否フラグを取得する
     *
     * @param value 値
     * @return 利用可否フラグ
     */
    public static AvailableFlg getEnum(String value) {
        return getEnum(value, null);
    }

    /**
     * 引数の値の利用可否フラグを取得する
     *
     * @param value        値
     * @param defaultValue デフォルト値
     * @return 利用可否フラグ
     */
    public static AvailableFlg getEnum(String value, AvailableFlg defaultValue) {
        for (AvailableFlg e : values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return defaultValue;
    }
}
