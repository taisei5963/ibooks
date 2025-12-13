package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ブック（検索用）モデル
 */
@Builder(toBuilder = true)
@Getter
public class SearchCategoryBookModel {
    /** ブックID */
    private Long bookId;

    /** カテゴリID1 */
    private Long categoryId1;

    /** カテゴリID2 */
    private Long categoryId2;

    /** カテゴリID3 */
    private Long categoryId3;

    /** カテゴリID */
    private Long categoryId;

    /** タイトル */
    private String title;

    /** 著者 */
    private String author;

    /** 出版社 */
    private String publisher;

    /** カテゴリコード */
    private String categoryCode;

    /** カテゴリ名 */
    private String categoryName;

    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
