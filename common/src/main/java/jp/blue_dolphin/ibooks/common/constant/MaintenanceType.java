package jp.blue_dolphin.ibooks.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * メンテナンスタイプ
 */
@AllArgsConstructor
@Getter
public enum MaintenanceType {
    IMMEDIATE("0", "即時"),
    DATETIME("1", "日時");

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;

    /**
     * 引数の値のメンテナンスタイプを取得する
     *
     * @param value 値
     * @return メンテナンスタイプ
     */
    public static MaintenanceType getEnum(String value) {
        for (MaintenanceType e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * メンテンナンスタイプリストを取得する
     *
     * @return メンテナンスタイプリスト
     */
    public static List<Map<String, String>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        for (MaintenanceType type : values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", type.getValue());
            map.put("description", type.getDescription());
            list.add(map);
        }
        return list;
    }
}
