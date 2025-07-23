package com.ibooks.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 検索結果
 *
 * @param <Z>
 */
@AllArgsConstructor
public class SearchResult<Z> {
    /** データリスト */
    @Getter
    private List<Z> list;
    /** データ件数 */
    @Getter
    private long count;

    /**
     * 検索結果が 0 件もしくは null の場合に true を返却する
     * @return {@code true} 0 件もしくは null
     */
    public boolean isEmpty() {
        return this.list == null || this.list.isEmpty();
    }
}
