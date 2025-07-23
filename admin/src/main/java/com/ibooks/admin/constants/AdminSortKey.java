package com.ibooks.admin.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理者ソートキー
 */
@AllArgsConstructor
@Getter
public enum AdminSortKey {
    /** ログインID */
    LOGIN_ID("login_id"),
    /** 最終ログイン日時 */
    LAST_LOGIN_DATE("last_login_date"),
    /** ログイン可否フラグ */
    LOGIN_FLG("login_flg");

    /** キー */
    private final String key;
}
