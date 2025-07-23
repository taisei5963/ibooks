package com.ibooks.admin.request;

import com.ibooks.admin.constants.AdminSortKey;
import com.ibooks.common.constants.SortValue;
import com.ibooks.common.request.SearchForm;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 管理者検索フォーム
 */
@Data
public class AdminSearchForm implements SearchForm {
    /** ソートキー */
    private AdminSortKey sortKey;
    /** ソート値 */
    private SortValue sortValue;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getSearchParamNames() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSearchParamValueRaw(String names) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderBy() {
        if (Objects.isNull(this.sortKey)) {
            return getOrderBy("admin_id", SortValue.ASC);
        }
        return getOrderBy(this.sortKey.getKey(), this.sortValue);
    }
}
