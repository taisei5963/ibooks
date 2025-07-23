package com.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibooks.common.constants.AccountStatus;
import com.ibooks.common.constants.LoginFlg;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * レビュワーモデル
 */
@Getter
@Builder(toBuilder = true)
public class ReviewerModel {
    /** レビュワーID */
    private Long reviewerId;
    /** レビュワー名 */
    private String name;
    /** ログインID */
    private String loginId;
    /** パスワード */
    private String password;
    /** アカウントステータス */
    private AccountStatus accountStatus;
    /** ログイン可否フラグ */
    private LoginFlg loginFlg;
    /** ログイン失敗回数 */
    private Integer loginFailureCount;
    /** ログイン失敗日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime loginFailureDate;
    /** 最終ログイン日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastLoginDate;
    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createDate;
    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateDate;
    /** 作成者ID */
    private String createId;
    /** バージョン */
    private Integer ver;
}
