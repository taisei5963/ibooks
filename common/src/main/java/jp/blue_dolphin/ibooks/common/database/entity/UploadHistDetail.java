package jp.blue_dolphin.ibooks.common.database.entity;

import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import java.time.LocalDateTime;

/**
 * アップロード履歴詳細エンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "upload_hist_detail")
public class UploadHistDetail {
    /** アップロード履歴詳細ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_hist_detail_id")
    public final Long uploadHistDetailId;

    /** アップロード履歴ID */
    @Column(name = "upload_hist_id")
    public final Long uploadHistId;

    /** ログレベル */
    @Column(name = "log_level")
    public final String logLevel;

    /** 内容 */
    @Column(name = "content")
    public final String content;

    /** 作成日時 */
    @Column(name = "created_at")
    public final LocalDateTime createdAt;

    /** 更新日時 */
    @Column(name = "updated_at")
    public final LocalDateTime updatedAt;
}
