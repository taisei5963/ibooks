package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * カテゴリモデル
 */
@Builder(toBuilder = true)
@Getter
public class CategoryModel {
    /** カテゴリID */
    private Long categoryId;

    /** カテゴリコード */
    private String categoryCode;

    /** カテゴリ名 */
    private String categoryName;

    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /** 作成者ID */
    private String createdId;

    /** バージョン */
    private Integer ver;
}
