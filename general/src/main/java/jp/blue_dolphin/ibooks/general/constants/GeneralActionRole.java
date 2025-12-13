package jp.blue_dolphin.ibooks.general.constants;

import jp.blue_dolphin.ibooks.common.constant.MapValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 一般サイト アクション権限
 */
@AllArgsConstructor
@Getter
public enum GeneralActionRole implements MapValue {
    /** 書籍管理 */
    BOOK("book", "書籍管理", new String[]{"book"});

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;
    /** アクション */
    private final String[] actions;

    /**
     * 引数の値のアクション権限を取得する
     *
     * @param value 値
     * @return アクション権限
     */
    public static GeneralActionRole getEnum(String value) {
        for (GeneralActionRole e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
