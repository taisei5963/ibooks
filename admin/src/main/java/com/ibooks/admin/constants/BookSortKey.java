package com.ibooks.admin.constants;

import com.ibooks.common.constants.SortKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ブックソートキー
 */
@AllArgsConstructor
@Getter
public enum BookSortKey implements SortKey {
    /** ブックID */
    BOOK_ID("book_id"),
    /** カテゴリID1 */
    CATEGORY_ID_1("category_id_1"),
    /** カテゴリID2 */
    CATEGORY_ID_2("category_id_2"),
    /** カテゴリID3 */
    CATEGORY_ID_3("category_id_3"),
    /** タイトル */
    TITLE("title"),
    /** 作者1 */
    AUTHOR("author"),
    /** 出版社 */
    PUBLISHER("publisher");

    /** キー */
    private final String key;
}
