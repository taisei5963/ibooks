package jp.blue_dolphin.ibooks.common.database.entity;

import jp.blue_dolphin.ibooks.common.constant.Flag;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * レビューエンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "review")
public class Review {
    /** レビューID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    public final Long reviewId;

    /** ブックID */
    @Column(name = "bookId")
    public final Long bookId;

    /** レビュワーID */
    @Column(name = "reviewer_id")
    public final Long reviewerId;

    /** 評価値 */
    @Column(name = "rating")
    public final Integer rating;

    /** コメント */
    @Column(name = "comment")
    public final String comment;

    /** レビュー表示フラグ */
    @Column(name = "review_view_flag")
    public final Flag reviewViewFlag;

    /** レビュー日 */
    @Column(name = "review_date")
    public final LocalDate reviewDate;

    /** 作成日時 */
    @Column(name = "created_at")
    public final LocalDateTime createdAt;

    /** 更新日時 */
    @Column(name = "updated_at")
    public final LocalDateTime updatedAt;

    /** 削除日時 */
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