package jp.blue_dolphin.ibooks.common.database.entity;

import jp.blue_dolphin.ibooks.common.constant.MaintenanceStatus;
import jp.blue_dolphin.ibooks.common.constant.MaintenanceType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
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
 * メンテナンスエンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "maintenance")
public class Maintenance {
    /** メンテナンスID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    public final Long maintenanceId;

    /** 名称 */
    @Column(name = "name")
    public final String name;

    /** メンテナンスタイプ */
    @Column(name = "type")
    public final MaintenanceType type;

    /** サイト種別 */
    @Column(name = "site_type")
    public final SiteType siteType;

    /** ステータス */
    @Column(name = "status")
    public final MaintenanceStatus status;

    /** 適用開始日時 */
    @Column(name = "effective_from")
    public final LocalDateTime effectiveFrom;

    /** 適用終了日時 */
    @Column(name = "effective_to")
    public final LocalDateTime effectiveTo;

    /** メッセージ */
    @Column(name = "message")
    public final String message;

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
