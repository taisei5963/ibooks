package com.ibooks.common.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * バリデーションエラーDTO
 */
@Getter
@Builder
public class ValidationErrorDto {
    /** フィールド名 */
    private String fieldName;
    /** エラーメッセージ */
    private String message;
}
