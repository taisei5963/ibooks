package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ブックソートキー
 */
@Getter
@AllArgsConstructor
public enum BookSortKey {
    /** ブックID */
    BOOK_ID("book_id"),
    /** ブックコード */
    BOOK_CODE("book_code"),
    /** タイトル */
    TITLE("title"),
    /** 作者 */
    AUTHOR("author"),
    /** 出版社 */
    PUBLISHER("publisher"),
    /** カテゴリ名 */
    CATEGORY_NAME("category_name");

    /** キー */
    private final String key;
}
