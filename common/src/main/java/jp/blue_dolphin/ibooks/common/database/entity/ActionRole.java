package jp.blue_dolphin.ibooks.common.database.entity;

import jp.blue_dolphin.ibooks.common.constant.AvailableFlg;
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
 * アクションロールエンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "action_role")
public class ActionRole {
    /** アクションロールID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_role_id")
    public final Long actionRoleId;

    /** アクションパス */
    @Column(name = "path")
    public final String path;

    /** 権限ID */
    @Column(name = "privilege_id")
    public final Long privilegeId;

    /** 利用可否フラグ */
    @Column(name = "available_flg")
    public final AvailableFlg availableFlg;

    /** 備考 */
    @Column(name = "note")
    public final String note;

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
