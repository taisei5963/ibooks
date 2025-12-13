package jp.blue_dolphin.ibooks.common.request;

import jp.blue_dolphin.ibooks.common.constant.SortKey;
import jp.blue_dolphin.ibooks.common.constant.SortValue;
import jp.blue_dolphin.ibooks.common.constant.SystemConst;
import jp.blue_dolphin.ibooks.common.dto.OrderBy;
import jp.blue_dolphin.ibooks.common.util.Strings;
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
     * 検索用ペラメータ名リストを返却する
     *
     * @return 検索用パラメータ名リスト
     */
    @NotNull List<String> getSearchParamNames();

    /**
     * 検索用パラメータの値を取得する
     *
     * @param paramName 検索用パラメータ名
     * @return 検索用パラメータの値
     */
    String getSearchParamValueRaw(String paramName);

    /**
     * リダイレクト用のパラメータを取得する
     *
     * @param sortKey   ソートキー
     * @param sortValue ソート値
     * @return リダイレクト用のパラメータ
     */
    default <K extends SortKey, V extends SortValue> Map<String, String> getRedirectParams(
            K sortKey, V sortValue) {
        Map<String, String> paramMap = new HashMap<>();
        for (String key : getSearchParamNames()) {
            String value = getSearchParamValueRaw(key);
            if (!value.isEmpty()) {
                paramMap.put(key, value);
            }
        }
        if (!Objects.isNull(sortKey)) {
            paramMap.put("sortKey", sortKey.name());
        }
        if (!Objects.isNull(sortValue)) {
            paramMap.put("sortValue", sortValue.getValue());
        }
        return paramMap;
    }

    /**
     * 検索用パラメータの値を取得する<br>
     * ※ null の場合は空文字を返却する
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
     * ソート順を返却する<br>
     * 例："order by id asc, name desc"
     *
     * @return ソート順
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
        if (sortKey == null || sortKey.isEmpty()) {
            return "";
        }
        return OrderBy.createOf(sortKey, sortValue).toString();
    }

    /**
     * LIKE 検索用のパラメータを返却する<br>
     * ※部分一致
     *
     * @param paramName パラメータ名
     * @return LIKE 検索用のパラメータ
     */
    default String getLikeParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (value == null || value.isEmpty()) {
            return null;
        }
        value = value.replace("%", "\\%").replace("_", "\\_");
        return "%" + value + "%";
    }

    /**
     * LIKE 検索用のパラメータを返却する<br>
     * ※前方一致
     *
     * @param paramName パラメータ名
     * @return LIKE 検索用(前方一致)のパラメータ
     */
    default String getForwardLikeParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (value == null || value.isEmpty()) {
            return null;
        }
        value = value.replace("%", "\\%").replace("_", "\\_");
        return value + "%";
    }

    /**
     * リスト型パラメータを返却する<br>
     * ※null や空文字でもカラリストを返却する
     *
     * @param paramName パラメータ名
     * @return 値リスト
     */
    default List<String> getListParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (value == null || value.isEmpty()) {
            return List.of();
        }
        String[] arr = value.split(SystemConst.LIST_PARAMETER_DELIMITER);
        List<String> list = new ArrayList<>();
        for (String val : arr) {
            if (!val.isBlank()) {
                list.add(val);
            }
        }
        return list;
    }

    /**
     * 検索用のパラメータを返却する<br>
     * 空文字の場合は null を返却する
     *
     * @param paramName パラメータ名
     * @return 検索用のパラメータ
     */
    default String getParam(String paramName) {
        String value = getSearchParamValueRaw(paramName);
        if (value != null && !value.isEmpty()) {
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
        if (value == null || value.isEmpty()) {
            return null;
        }
        if (!value.matches("^[0-9]+$")) {
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
        if (value == null || value.isEmpty()) {
            return null;
        }
        if (!value.matches("^[0-9]+$")) {
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
     * 検索用オプションを取得する
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
     * パラメータの設定判定から除外したパラメータ名を返却する
     *
     * @return パラメータ名
     */
    default List<String> excludeSetParamNames() {
        return List.of();
    }
}
