package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * アップロード種別
 */
@Getter
@AllArgsConstructor
public enum UploadType {
    BOOK("01", "ブックCSVアップロード", false, false);

    /** 値 */
    private final String value;
    /** 説明 */
    private final String description;
    /** レビュワーサイトで使用する場合は true */
    private final boolean useReviewerSite;
    /** 一般サイトで使用する場合は true */
    private final boolean useGeneralSite;

    /**
     * 引数の値の UploadType 列挙型を返却する
     * @param value 値
     * @return UploadType 列挙型
     */
    public static UploadType geEnum(String value) {
        for (UploadType type : UploadType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
