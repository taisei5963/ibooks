package com.ibooks.common.database.entity;

import com.ibooks.common.constants.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

import java.time.LocalDateTime;

/**
 * レビュー
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "review")
@AllArgsConstructor
public class Review {

    /** レビューID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    public final Long reviewId;

    /** ブックID */
    @Column(name = "book_id")
    public final Long bookId;

    /** レビュワーID */
    @Column(name = "reviewer_id")
    public final Long reviewerId;

    /** 評価 */
    @Column(name = "rating")
    public final Integer rating;

    /** コメント */
    @Column(name = "comment")
    public final String comment;

    /** レビューステータス */
    @Column(name = "review_status")
    public final ReviewStatus reviewStatus;

    /** 作成日 */
    @Column(name = "created_at")
    public final LocalDateTime createdAt;

    /** 更新日 */
    @Column(name = "updated_at")
    public final LocalDateTime updatedAt;

    /** 削除日 */
    @Column(name = "deleted_at")
    public final LocalDateTime deletedAt;

    /** 作成者ID */
    @Column(name = "created_id")
    public final String createdId;

    /** バージョン */
    @Version
    @Column(name = "ver")
    public final Integer ver;
}
