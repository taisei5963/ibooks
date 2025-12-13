package jp.blue_dolphin.ibooks.common.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * バリデーションエラーDTO
 */
@Getter
@Builder
public class ValidationErrDto {
    /** フィールド名 */
    private String fieldName;
    /** エラーメッセージ */
    private String message;
}
