package jp.blue_dolphin.ibooks.common.database.entity;

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
 * ブックチャプターエンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable=true)
@Table(name = "book_chapter")
public class BookChapter {
    /** ブックチャプターID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_chapter_id")
    public final Long bookChapterId;

    /** ブックID */
    @Column(name = "book_id")
    public final Long bookId;

    /** 章 */
    @Column(name = "chapter")
    public final String chapter;

    /** ソート順 */
    @Column(name = "sort_order")
    public final Integer sortOrder;

    /** タイトル */
    @Column(name = "title")
    public final String title;

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
