package com.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * カテゴリモデル
 */
@Getter
@Builder(toBuilder = true)
public class CategoryModel {
    /** 「その他」のカテゴリコード */
    public static final String CATEGORY_CODE_OTHERS = "9999";
    /** カテゴリID */
    private Long categoryId;
    /** カテゴリコード */
    private String categoryCode;
    /** カテゴリ名 */
    private String categoryName;
    /** ソート値 */
    private Integer sortNo;
    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createDate;
    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateDate;
    /** 作成者ID */
    private String createId;
    /** バージョン */
    private Integer ver;
}
