package jp.techcof.ITive.common.database.entity;

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
 * ブックガイドエンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable=true)
@Table(name = "book_guide")
public class BookGuide {
    /** ブックガイドID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_guide_id")
    public final Long bookGuideId;

    /** ブックID */
    @Column(name = "book_id")
    public final Long bookId;

    /** おすすめ対象者 */
    @Column(name = "recommend_for")
    public final String recommendFor;

    /** おすすめタイミング */
    @Column(name = "recommended_timing")
    public final String recommendedTiming;

    /** おすすめポイント */
    @Column(name = "recommended_points")
    public final String recommendedPoints;

    /** 次のおすすめ */
    @Column(name = "next_recommended")
    public final String nextRecommended;

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
