package jp.blue_dolphin.ibooks.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レベル
 */
@Getter
@AllArgsConstructor
public enum Level {
    NONE("0", "なし"),
    BEGINNER("1", "初級"),
    INTERMEDIATE("2", "中級"),
    ADVANCED("3", "上級");

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;

    /**
     * 引数の難易度を取得し、存在しない値の場合はNONEを返却する
     *
     * @param value 値
     * @return 難易度
     */
    public static Level getEnum(String value) {
        for (Level e : values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return Level.NONE;
    }

    /**
     * 引数の値の列挙型の説明を取得する
     *
     * @param name 名称
     * @return 列挙型の説明
     */
    public static String getDescription(String name) {
        try {
            return Level.valueOf(name).getDescription();
        } catch (IllegalArgumentException | NullPointerException e) {
            return Level.NONE.getDescription();
        }
    }
}
