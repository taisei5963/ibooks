package com.ibooks.common.constants;

/**
 * ソートキー
 */
public interface SortKey {
    /**
     * ソートするキーを取得する
     *
     * @return ソートするキー
     */
    String getKey();

    /**
     * ソートする列挙子を取得する
     *
     * @return ソートする列挙子
     */
    String name();
}
