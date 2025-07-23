package com.ibooks.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レビューステータス
 */
@Getter
@AllArgsConstructor
public enum ReviewStatus {
    PUBLIC("一般公開"),
    PRIVATE("限定公開"),
    DELETED("非公開");

    /** 説明 */
    private final String description;
}
