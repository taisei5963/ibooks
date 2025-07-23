package com.ibooks.common.dto;

/**
 * ユーザー情報
 */
public interface UserInfo {
    /**
     * ID を取得する
     *
     * @return ID
     */
    String getId();

    /**
     * 管理者でログインしているか
     *
     * @return {@code true} 管理者でログインしている
     */
    boolean isAdminLogin();

}
