package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * アップロード履歴ソートキー
 */
@AllArgsConstructor
@Getter
public enum UploadHrSortKey implements SortKey{
    /** 登録日時 */
    CREATED_AT("CREATED_AT");

    /** キー */
    private final String key;
}
