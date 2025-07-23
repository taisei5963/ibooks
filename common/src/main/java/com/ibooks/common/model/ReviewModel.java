package com.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibooks.common.constants.ReviewStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * レビューモデル
 */
@Getter
@Builder(toBuilder = true)
public class ReviewModel {
    /** レビューID */
    private Long reviewId;
    /** ブックID */
    private Long bookId;
    /** レビュワーID */
    private Long reviewerId;
    /** 評価 */
    private Integer rating;
    /** コメント */
    private String comment;
    /** レビューステータス */
    private ReviewStatus reviewStatus;
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
    /** ユーザー名（表示用） */
    @Setter
    private String userName;
}
