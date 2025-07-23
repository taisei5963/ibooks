package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * サイト種別
 */
@Getter
@AllArgsConstructor
public enum SiteType {
    ADMIN("管理者サイト", "A"),
    REVIEWER("レビュワーサイト", "R"),
    GENERAL("一般サイト", "G");

    /** 説明 */
    private final String description;
    /** 識別子 */
    private final String identifier;
}
