package jp.blue_dolphin.ibooks.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * メンテナンスステータス
 */
@AllArgsConstructor
@Getter
public enum MaintenanceStatus implements MapValue {
    ENABLED("1", "有効"),
    DISABLED("0", "無効");

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;

    /**
     * 引数の値のメンテナンスステータスを取得する
     *
     * @param value 値
     * @return メンテナンスステータス
     */
    public static MaintenanceStatus getEnum(String value) {
        for (MaintenanceStatus e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * メンテナンスステータスリストを取得する
     *
     * @return メンテナンスステータスリスト
     */
    public static List<Map<String, String>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        for (MaintenanceStatus status : values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", status.getValue());
            map.put("description", status.getDescription());
            list.add(map);
        }
        return list;
    }
}
