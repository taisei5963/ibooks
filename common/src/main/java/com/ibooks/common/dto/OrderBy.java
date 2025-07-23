package com.ibooks.common.dto;

import com.ibooks.common.constants.SortValue;

import java.util.ArrayList;
import java.util.List;

/**
 * ソート順
 */
public class OrderBy {
    /** ソートキー */
    List<String> keys;
    /** ソート順 */
    List<SortValue> values;

    /**
     * コンストラクタ
     */
    private OrderBy() {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    /**
     * ソート順を追加する
     *
     * @param key   ソートキー
     * @param value ソート値
     * @return ソート順
     */
    public OrderBy add(String key, SortValue value) {
        this.keys.add(key);
        this.values.add(value != null ? value : SortValue.ASC);
        return this;
    }

    /**
     * ソート順を作成する
     *
     * @param key   ソートキー
     * @param value ソート値
     * @return ソート順
     */
    public static OrderBy create(String key, SortValue value) {
        OrderBy orderBy = new OrderBy();
        orderBy.add(key, value);
        return orderBy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (this.keys.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("order by ");
        for (int i = 0, size = this.keys.size(); i < size; i++) {
            if (i >0) {
                sb.append(", ");
            }
            sb.append(this.keys.get(i));
            sb.append(" ");
            sb.append(this.values.get(i).getValue());
        }
        return sb.toString();
    }
}