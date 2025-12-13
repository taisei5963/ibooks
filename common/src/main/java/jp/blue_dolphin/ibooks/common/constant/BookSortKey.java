package jp.blue_dolphin.ibooks.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ブックソートキー
 */
@AllArgsConstructor
@Getter
public enum BookSortKey implements SortKey {
    /** JANコード */
    JANCODE("jan_code"),
    /** タイトル */
    TITLE("title"),
    /** 出版社 */
    PUBLISHER("publisher");

    /** キー */
    private final String key;
}
