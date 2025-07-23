package com.ibooks.common.constants;

import lombok.Getter;

/**
 * シーケンスタイプ
 */
public enum SequenceType {
    IMG_TMP_SEQ(1, "一時画像ファイル", "", 0);

    /** 値 */
    @Getter
    private final Integer value;
    /** 説明 */
    @Getter
    private final String description;
    /** プレフィックス */
    @Getter
    private final String prefix;
    /** シーケンスをサイクルしたい場合に定義し、0 の場合はサイクルしない */
    @Getter
    private final Integer cycle;

    /**
     * コンストラクタ
     *
     * @param value       値
     * @param description 説明
     * @param prefix      プレフィックス
     * @param cycle       サイクル
     */
    SequenceType(Integer value, String description, String prefix, Integer cycle) {
        this.value = value;
        this.description = description;
        this.prefix = prefix;
        this.cycle = cycle;
    }

    /**
     * 引数の値のシーケンスタイプを取得する
     *
     * @param value 値
     * @return シーケンスタイプ
     */
    public static SequenceType getEnum(String value) {
        return getEnum(Integer.valueOf(value));
    }

    /**
     * 引数の値のシーケンスタイプを取得する
     *
     * @param value 値
     * @return シーケンスタイプ
     */
    public static SequenceType getEnum(Integer value) {
        for (SequenceType enums : SequenceType.values()) {
            if (enums.value.equals(value)) {
                return enums;
            }
        }
        return null;
    }
}
