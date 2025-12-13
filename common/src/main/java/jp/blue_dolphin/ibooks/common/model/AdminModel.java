package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.blue_dolphin.ibooks.common.constant.AccountStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 管理者モデル
 */
@Builder(toBuilder = true)
@Getter
public class AdminModel {
    /** 管理者ID */
    private Long adminId;

    /** ログインID */
    private String loginId;

    /** 権限ID */
    private Long privilegeId;

    /** パスワード */
    private String password;

    /** 管理者名 */
    private String name;

    /** アカウントステータス */
    private AccountStatus accountStatus;

    /** 最終ログイン日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastLoginDate;

    /** ログイン失敗回数 */
    private Integer loginFailureCount;

    /** ログイン失敗日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime loginFailureDate;

    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /** 作成者ID */
    private String createdId;

    /** バージョン */
    private Integer ver;
}
