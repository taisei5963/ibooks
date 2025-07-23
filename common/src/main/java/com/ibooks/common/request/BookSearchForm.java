package com.ibooks.common.request;

import com.ibooks.common.constants.BookSortKey;
import com.ibooks.common.constants.SortValue;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ブック検索フォーム
 */
@Setter
@Getter
public class BookSearchForm implements SearchForm{
    /** ブックコード */
    private String schBookCode;
    /** タイトル */
    private String schTitle;
    /** 作者 */
    private String schAuthor;
    /** 出版社 */
    private String schPublisher;
    /** カテゴリ */
    private String schCategory;

    /** ソートキー */
    private BookSortKey sortKey;
    /** ソート値 */
    private SortValue sortValue;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getSearchParamNames() {
        List<String> params = new ArrayList<>();
        params.add("schBookCode");
        params.add("schTitle");
        params.add("schAuthor");
        params.add("schPublisher");
        params.add("schCategory");
        return params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSearchParamValueRaw(String paramName) {
        if (Objects.isNull(paramName)) {
            return null;
        }
        return switch (paramName) {
            case "schBookCode" -> getSchBookCode();
            case "schTitle" -> getSchTitle();
            case "schAuthor" -> getSchAuthor();
            case "schPublisher" -> getSchPublisher();
            case "schCategory" -> getSchCategory();
            default -> null;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderBy() {
        if (this.sortKey == null) {
            return getOrderBy(BookSortKey.BOOK_ID.getKey(), SortValue.ASC);
        }
        return getOrderBy(this.sortKey.getKey(), this.sortValue);
    }
}
