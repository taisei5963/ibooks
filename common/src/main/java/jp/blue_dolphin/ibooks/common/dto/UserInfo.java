package jp.blue_dolphin.ibooks.common.dto;

/**
 * ユーザ情報インタフェース
 */
public interface UserInfo {
    /**
     * ID を取得する
     * @return ID
     */
    String getId();

    /**
     * 管理者でログインしているか
     * @return {@code true} 管理者でログイン
     */
    boolean isAdminLogin();
}
