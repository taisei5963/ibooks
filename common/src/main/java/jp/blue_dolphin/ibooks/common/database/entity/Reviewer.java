package jp.blue_dolphin.ibooks.common.database.entity;

import jp.blue_dolphin.ibooks.common.constant.AccountStatus;
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
 * レビュワーエンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "reviewer")
public class Reviewer {
    /** レビュワーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewer_id")
    public final Long reviewerId;

    /** ログインID */
    @Column(name = "login_id")
    public final String loginId;

    /** パスワード */
    @Column(name = "password")
    public final String password;

    /** レビュワー名 */
    @Column(name = "name")
    public final String name;

    /** アカウントステータス */
    @Column(name = "account_status")
    public final AccountStatus accountStatus;

    /** 最終ログイン日時 */
    @Column(name = "last_login_date")
    public final LocalDateTime lastLoginDate;

    /** ログイン失敗回数 */
    @Column(name = "login_failure_count")
    public final Integer loginFailureCount;

    /** ログイン失敗日時 */
    @Column(name = "login_failure_date")
    public final LocalDateTime loginFailureDate;

    /** パスワード変更期限 */
    @Column(name = "password_remind_date")
    public final LocalDateTime passwordRemindDate;

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
