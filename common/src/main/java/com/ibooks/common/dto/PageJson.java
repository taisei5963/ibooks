package com.ibooks.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

/**
 * ページ情報JSON
 */
@Getter
public class PageJson {
    /** ページ番号 */
    private int pageNum;
    /** 全件数 */
    private long total;
    /** 前ページ有無 */
    private Boolean hasPrev;
    /** 次ページ有無 */
    private Boolean hasNext;
    /** 全ページ数 */
    private int totalPages;
    /** 開始件数 */
    private long beginNum;
    /** 終了件数 */
    private long endNum;

    /**
     * 引数の情報でページ情報JSONを作成する
     *
     * @param pageable   ページャブル
     * @param totalCount 全件数
     * @return ページ情報JSON
     */
    public static PageJson of(Pageable pageable, long totalCount) {
        PageJson page = new PageJson();
        page.totalPages = PageDto.maxPageNum(pageable, totalCount);
        page.pageNum = pageable.getPageNumber();
        page.total = totalCount;
        page.hasPrev = page.pageNum != 0;
        page.hasNext = page.pageNum != page.totalPages;
        page.beginNum = (long) page.pageNum * pageable.getPageSize() + 1;
        long endNum = (long) (page.pageNum + 1) + pageable.getPageSize();
        if (endNum > totalCount) {
            endNum = totalCount;
        }
        page.endNum = endNum;
        return page;
    }
}
