package jp.blue_dolphin.ibooks.common.constant;

/**
 * ソートキーインタフェース
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
