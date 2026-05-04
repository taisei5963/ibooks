package jp.blue_dolphin.ibooks.admin.request;

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
    /** タイトル */
    private String schTitle;
    /** 著者 */
    private String schAuthor;
    /** 出版社 */
    private String schPublisher;
    /** カテゴリ */
    private Long schCategory;

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
            case "schTitle" -> getSchTitle();
            case "schAuthor" -> getSchAuthor();
            case "schPublisher" -> getSchPublisher();
            case "schCategory" -> Objects.toString(getSchCategory(), null);
            default -> null;
        };
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
