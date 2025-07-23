package com.ibooks.common.database.entity;

import com.ibooks.common.constants.AccountStatus;
import com.ibooks.common.constants.LoginFlg;
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
 * 管理者
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "admin")
public class Admin {
    /** 管理者ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    public final Long adminId;

    /** ログインID */
    @Column(name = "loginId")
    public final String loginId;

    /** パスワード */
    @Column(name = "password")
    public final String password;

    /** 管理者名 */
    @Column(name = "name")
    public final String name;

    /** アカウントステータス */
    @Column(name = "account_status")
    public final AccountStatus accountStatus;

    /** ログイン可否フラグ */
    @Column(name = "login_flg")
    public final LoginFlg loginFlg;

    /** ログイン失敗回数 */
    @Column(name = "login_failure_count")
    public final Integer loginFailureCount;

    /** ログイン失敗日時 */
    @Column(name = "login_failure_date")
    public final LocalDateTime loginFailureDate;

    /** 最終ログイン日時 */
    @Column(name = "last_login_date")
    public final LocalDateTime lastLoginDate;

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
