package com.ibooks.common.request;

import com.ibooks.common.constants.Regex;
import com.ibooks.common.constants.SortKey;
import com.ibooks.common.constants.SortValue;
import com.ibooks.common.constants.SystemConst;
import com.ibooks.common.dto.OrderBy;
import com.ibooks.common.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 検索フォーム
 */
public interface SearchForm {
    /**
     * 検索用パラメーター名リストを返却する
     *
     * @return 検索用パラメータ名リスト
     */
    @NotNull List<String> getSearchParamNames();

    /**
     * 検索用のパラメータ名を取得する
     *
     * @param param 検索用パラメータ名
     * @return 検索用パラメータの値
     */
    String getSearchParamValueRaw(String param);

    /**
     * リダイレクト用のパラメータを取得する
     *
     * @param sortKey   ソートキー
     * @param sortValue ソート値
     * @return リダイレクト用のパラメータ
     */
    default <K extends SortKey, V extends SortValue> Map<String, String> getRedirectParams(
            K sortKey, V sortValue) {

        Map<String, String> params = new HashMap<>();
        for (String key : getSearchParamNames()) {
            String value = getSearchParamValue(key);
            if (!value.isEmpty()) {
                params.put(key, value);
            }
        }
        if (!Objects.isNull(sortKey)) {
            params.put("sortKey", sortKey.name());
        }
        if (!Objects.isNull(sortValue)) {
            params.put("sortValue", sortValue.getValue());
        }
        return params;
    }

    /**
     * 検索用パラメータの値を取得する
     *
     * @param paramName 検索用パラメータ名
     * @return 検索用パラメータの値
     */
    default @NotNull String getSearchParamValue(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (Objects.isNull(value)) {
            return "";
        }
        return value;
    }

    /**
     * ソート順を返却する
     *
     * @return ソート値
     */
    String getOrderBy();

    /**
     * 引数でソート順を作成する
     *
     * @param sortKey   ソートキー
     * @param sortValue ソート値
     * @return ソート順
     */
    default String getOrderBy(String sortKey, SortValue sortValue) {
        if (Objects.isNull(sortKey) || sortKey.isEmpty()) {
            return "";
        }
        return OrderBy.create(sortKey, sortValue).toString();
    }

    /**
     * Like 検索用パラメータを返却する
     *
     * @param paramName パラメータ名
     * @return Like 検索用パラメータ
     */
    default String getLinkParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (Objects.isNull(value) || value.isEmpty()) {
            return null;
        }
        value = value.replace("%", "\\%").replace("_", "\\_");
        return "%" + value + "%";
    }

    /**
     * リスト型パラメータを返却する<br>
     * ※null や空文字でも空リストを返却する
     *
     * @param paramName パラメータ名
     * @return 値リスト
     */
    default List<String> getListParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (Objects.isNull(value) || value.isEmpty()) {
            return List.of();
        }
        String[] array = value.split(SystemConst.LIST_PARAM_DELIMITER);
        List<String> list = new ArrayList<>();
        for (String val : array) {
            if (!val.isBlank()) {
                list.add(val);
            }
        }
        return list;
    }

    /**
     * Like 検索用のパラメータを返却する<br>
     * ※前方一致用
     *
     * @param paramName パラメータ名
     * @return Like 検索（前方一致）用のパラメータ
     */
    default String getForwardLikeParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (Objects.isNull(value) || value.isEmpty()) {
            return null;
        }
        value = value.replace("%", "\\%").replace("_", "\\_");
        return value + "%";
    }

    /**
     * 検索用のパラメータを返却する<br>
     * 空文字の場合は null を返却する
     *
     * @param paramName パラメータ名
     * @return 検索用パラメータ
     */
    default String getParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (!Objects.isNull(value) || !value.isEmpty()) {
            return value;
        }
        return null;
    }

    /**
     * 検索用のパラメータを整数型で返却する<br>
     * 空文字の場合は null を返却する
     *
     * @param paramName パラメータ名
     * @return 検索用のパラメータ
     */
    default Integer getIntegerParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (Objects.isNull(value) || value.isEmpty()) {
            return null;
        }
        if (!Regex.NUMBER_REGEX.matches(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * 検索用のパラメータを整数型で返却する<br>
     * 空文字の場合は null を返却する
     *
     * @param paramName パラメータ名
     * @return 検索用のパラメータ
     */
    default Long getLongParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (Objects.isNull(value) || value.isEmpty()) {
            return null;
        }
        if (!Regex.NUMBER_REGEX.matches(value)) {
            return null;
        }
        return Long.valueOf(value);
    }

    /**
     * 検索用オプションを取得する
     *
     * @param pageable ページャブル
     * @return 検索用オプション
     */
    default SelectOptions getSelectOptions(Pageable pageable) {
        return createSelectOptions(pageable);
    }

    /**
     * 検索用オプションを作成する
     *
     * @param pageable ページャブル
     * @return 検索用オプション
     */
    static SelectOptions createSelectOptions(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        return SelectOptions.get().offset(offset).limit(limit).count();
    }

    /**
     * 検索パラメータが設定されている場合は true を返却する
     *
     * @return {@code true} パラメータあり
     */
    default boolean hasSearchParams() {
        List<String> excludeNames = excludeSetParamNames();
        for (String paramName : getSearchParamNames()) {
            if (!excludeNames.contains(paramName) && !Strings.isEmpty(getParam(paramName))) {
                return true;
            }
        }
        return false;
    }

    /**
     * パラメータの設定判定から除外したいパラメータ名を返却する
     *
     * @return パラメータ名リスト
     */
    default List<String> excludeSetParamNames() {
        return List.of();
    }
}
