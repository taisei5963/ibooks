package com.ibooks.admin.request;

import com.ibooks.common.constants.SortValue;
import com.ibooks.common.constants.UploadHrSortKey;
import com.ibooks.common.request.SearchForm;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * アップロード履歴検索フォーム
 */
@Data
public class UploadHrSearchForm implements SearchForm {
    /** アップロード種別 */
    private String schUploadType;
    /** アップロードステータス */
    private String schUploadStatus;
    /** 実行者 */
    private String schRegisterId;
    /** ソートキー */
    private UploadHrSortKey sortKey;
    /** ソート値 */
    private SortValue sortValue;
    /** リストのオフセット値 */
    private long offset = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getSearchParamNames() {
        List<String> paramsNames = new ArrayList<>();
        paramsNames.add("schUploadType");
        paramsNames.add("schUploadStatus");
        paramsNames.add("schRegisterId");
        return paramsNames;
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
            case "schUploadType" -> getSchUploadType();
            case "schUploadStatus" -> getSchUploadStatus();
            case "schRegisterId" -> getSchRegisterId();
            default -> null;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderBy() {
        if (Objects.isNull(this.sortKey)) {
            return getOrderBy(UploadHrSortKey.CREATED_AT.getKey(), SortValue.DESC);
        }
        return getOrderBy(this.sortKey.getKey(), this.sortValue);
    }
}
