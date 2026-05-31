package jp.blue_dolphin.ibooks.general.request;

import jp.blue_dolphin.ibooks.common.constant.BookSortKey;
import jp.blue_dolphin.ibooks.common.constant.SortValue;
import jp.blue_dolphin.ibooks.common.request.SearchForm;
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
public class BookSearchForm implements SearchForm {
    /** カテゴリ */
    private Long schCategory;
    /** レベル */
    private String schLevel;

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
        params.add("schCategory");
        params.add("schLevel");
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
        return Objects.toString(getSchCategory(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderBy() {
        if (this.sortKey == null) {
            return getOrderBy(BookSortKey.JANCODE.getKey(), SortValue.ASC);
        }
        return getOrderBy(this.sortKey.getKey(), this.sortValue);
    }
}
