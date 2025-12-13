package jp.blue_dolphin.ibooks.common.dto;

import jp.blue_dolphin.ibooks.common.constant.SortKey;
import jp.blue_dolphin.ibooks.common.constant.SortValue;
import jp.blue_dolphin.ibooks.common.request.SearchForm;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * ページ情報DTO
 */
public class PageDto {
    /** ページリンクの最大表示数 */
    public static final int MAX_DISPLAY_PAGES = 7;

    /** ページ番号 */
    @Getter
    private int pageNum;
    /** ソートキー */
    private SortKey sortKey;
    /** ソート値 */
    private SortValue sortValue;
    /** 全件数 */
    @Getter
    private long totalCount;
    /** 前ページ有無 */
    private Boolean hasPrev;
    /** 次ページ有無 */
    private Boolean hasNext;
    /** 全ページ数 */
    @Getter
    private int totalPages;
    /** 開始件数 */
    @Getter
    private long from;
    /** 終了件数 */
    @Getter
    private long to;
    /** 検索フォーム */
    private SearchForm searchForm;
    /** 開始ページ番号 */
    @Getter
    private int beginPageNum;
    /** 終了ページ番号 */
    @Getter
    private int endPageNum;
    /** 表示件数 */
    @Getter
    private int size;

    /**
     * コンストラクタ
     */
    private PageDto() {
        super();
    }

    /**
     * 引数の情報でページ情報DTOを作成する
     *
     * @param pageable   ページャブル
     * @param totalCount 全件数
     * @return ページ情報DTO
     */
    public static PageDto of(Pageable pageable, long totalCount) {
        return of(pageable, totalCount, null, null, null);
    }

    /**
     * 引数の条件でページ情報DTOを作成する
     *
     * @param pageable   ページャブル
     * @param totalCount 全件数
     * @param sortKey    ソートキー
     * @param sortValue  ソート値
     * @param searchForm 検索フォーム
     * @return ページ情報DTO
     */
    public static PageDto of(Pageable pageable, long totalCount, SortKey sortKey,
                             SortValue sortValue, SearchForm searchForm) {
        PageDto pageDto = new PageDto();
        pageDto.totalPages = maxPageNumber(pageable, totalCount);
        pageDto.pageNum = pageable.getPageNumber();
        pageDto.sortKey = sortKey;
        pageDto.sortValue = sortValue;
        pageDto.size = pageable.getPageSize();
        pageDto.totalCount = totalCount;
        pageDto.hasPrev = pageDto.pageNum != 0;
        pageDto.hasNext = pageDto.pageNum != pageDto.totalPages;
        pageDto.from = (long) pageDto.pageNum * pageable.getPageSize() + 1;
        long to = (long) (pageDto.pageNum + 1) * pageable.getPageSize();
        if (to > totalCount) {
            to = totalCount;
        }
        pageDto.to = to;
        pageDto.searchForm = searchForm;
        pageDto.beginPageNum = getBeginPageNumber(pageDto.pageNum, pageDto.totalPages);
        pageDto.endPageNum = pageDto.beginPageNum + MAX_DISPLAY_PAGES + 1;
        if (pageDto.endPageNum > pageDto.totalPages) {
            pageDto.endPageNum = pageDto.totalPages;
        }
        return pageDto;
    }

    /**
     * 最大ページ数を返却する
     *
     * @param pageable   ページャブル
     * @param totalCount 全件数
     * @return 最大ページ数
     */
    public static int maxPageNumber(Pageable pageable, long totalCount) {
        int totalPages = 0;
        if (totalCount > 0) {
            totalPages = ((int) (totalCount - 1) / pageable.getPageSize());
        }
        return totalPages;
    }

    /**
     * 前ページ番号を返却する
     *
     * @param pageable   ページャブル
     * @param totalCount 全件数
     * @return 前ページ番号
     */
    public static int prevPageNumber(Pageable pageable, long totalCount) {
        int prevPageNum = pageable.getPageNumber() - 1;
        int maxPageNum = maxPageNumber(pageable, totalCount);
        return Math.min(prevPageNum, maxPageNum);
    }

    /**
     * ソートキーを取得する
     *
     * @return ソートキー
     */
    public String getSortKey() {
        return this.sortKey == null ? "" : this.sortKey.name();
    }

    /**
     * ソート値を取得する
     *
     * @return ソート値
     */
    public String getSortValue() {
        return this.sortValue == null ? "" : this.sortValue.getValue();
    }

    /**
     * 引数のソートキーのソート値を取得する
     *
     * @param sortKey ソートキー
     * @return ソート値
     */
    public String getSortValue(String sortKey) {
        if (getSortKey().equals(sortKey) && getSortValue().equals(SortValue.ASC.getValue())) {
            return SortValue.DESC.getValue();
        }
        return SortValue.ASC.getValue();
    }

    /**
     * 前ページがあるかどうか
     *
     * @return {@code true} 前ページがある
     */
    public boolean hasPrev() {
        return this.hasPrev;
    }

    /**
     * 次ページがあるかどうか
     *
     * @return {@code true} 次ページがある
     */
    public boolean hasNext() {
        return this.hasNext;
    }

    /**
     * ページャー用のクエリーストリングを取得する
     *
     * @return クエリーストリング
     */
    public String getPagerQueryString() {
        StringJoiner sj = new StringJoiner("&");
        if (getSize() != 0) {
            sj.add("size=" + getSize());
        }
        if (!getSortKey().isEmpty()) {
            sj.add("sortKey=" + getSortKey());
            sj.add("sortValue=" + getSortValue());
        }
        return getQueryString(sj.toString());
    }

    /**
     * ソート用のクエリーストリングを取得する
     *
     * @return クエリーストリング
     */
    public String getSortQueryString() {
        String page = "";
        if (getSize() != 0) {
            page = "size=" + getSize() + "&";
        }
        page += "page=" + getPageNum();
        return getQueryString(page);
    }

    /**
     * 検索用のクエリーストリングを追加して返却する
     *
     * @param query クエリーストリング
     * @return クエリーストリング
     */
    private String getQueryString(String query) {
        boolean hasParams = false;
        StringJoiner sj = new StringJoiner("&", "?", "");
        if (Objects.nonNull(query) && !query.isEmpty()) {
            sj.add(query);
            hasParams = true;
        }
        if (this.searchForm != null) {
            for (String paramName : this.searchForm.getSearchParamNames()) {
                String value = this.searchForm.getSearchParamValue(paramName);
                if (value.isEmpty()) {
                    continue;
                }
                String sb = paramName + "=" + Strings.urlEncode(value);
                sj.add(sb);
                hasParams = true;
            }
        }
        if (!hasParams) {
            return "";
        }
        return sj.toString();
    }

    /**
     * hidden 用のパラメータリストを返却する
     *
     * @return hidden 用のパラメータリスト
     */
    public List<Map<String, String>> getHiddenParams() {
        List<Map<String, String>> params = new ArrayList<>();
        if (!getSortKey().isEmpty()) {
            params.add(hiddenParam("sortKey", getSortKey()));
            params.add(hiddenParam("sortValue", getSortValue()));
        }
        params.add(hiddenParam("page", String.valueOf(getPageNum())));
        if (this.searchForm != null) {
            List<String> paramNames = this.searchForm.getSearchParamNames();
            for (String paramName : paramNames) {
                String value = this.searchForm.getSearchParamValueRaw(paramName);
                if (value.isEmpty()) {
                    continue;
                }
                params.add(hiddenParam(paramName, value));
            }
        }
        return params;
    }

    /**
     * hidden 用のパラメータを作成する
     *
     * @param paramName パラメータ名
     * @param value     値
     * @return hidden 用のパラメータ
     */
    private Map<String, String> hiddenParam(String paramName, String value) {
        Map<String, String> map = new HashMap<>();
        map.put("name", paramName);
        map.put("value", value);
        return map;
    }

    /**
     * 開始ページを取得する
     *
     * @param pageNum    ページ番号
     * @param totalPages 全ページ数
     * @return 開始ページ番号
     */
    private static int getBeginPageNumber(int pageNum, int totalPages) {
        if (pageNum == 0) {
            return 0;
        }
        if (totalPages < MAX_DISPLAY_PAGES) {
            return 0;
        }
        if (MAX_DISPLAY_PAGES % 2 == 0) {
            int half = MAX_DISPLAY_PAGES / 2;
            if (pageNum < half) {
                return 0;
            }
            if (totalPages <= pageNum + half) {
                return totalPages - MAX_DISPLAY_PAGES + 1;
            }
            return pageNum - half + 1;
        } else {
            int half = MAX_DISPLAY_PAGES / 2;
            if (pageNum < half) {
                return 0;
            }
            if (totalPages <= pageNum + half) {
                return totalPages - MAX_DISPLAY_PAGES + 1;
            }
            return pageNum - half;
        }
    }
}
