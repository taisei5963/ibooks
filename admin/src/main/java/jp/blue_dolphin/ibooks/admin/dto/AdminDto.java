package jp.blue_dolphin.ibooks.admin.dto;

import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.model.AdminModel;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 管理者DTO
 */
@Component
public class AdminDto {
    /**
     * アカウントを取得する
     *
     * @return アカウント
     */
    public Account getAccount() {
        return Account.of(getAdminId(), getId(), getName(), SiteType.ADMIN.getIdentifier());
    }

    /**
     * 管理者IDを取得する
     *
     * @return 管理者ID
     */
    public Long getAdminId() {
        return adminSession().getAdminId();
    }

    /**
     * 管理者名を取得する
     *
     * @return 管理者名
     */
    public String getName() {
        return adminSession().getName();
    }

    /**
     * ログインIDを取得する
     *
     * @return ログインID
     */
    public String getLoginId() {
        return adminSession().getLoginId();
    }

    public Long getPrivilegeId() {
        return adminSession().getPrivilegeId();
    }

    /**
     * 機能リストを取得する
     *
     * @return 機能リスト
     */
    public List<String> getFunctions() {
        return adminSession().getFunctions();
    }

    /**
     * 実行不可アクションパスリストを取得する
     *
     * @return 実行不可アクションパスリスト
     */
    public List<String> getInaccessibleActionPaths() {
        return adminSession().getInaccessibleActionPaths();
    }

    /**
     * 引数の管理者モデルの情報を設定する
     *
     * @param model 管理者モデル
     */
    public void set(AdminModel model) {
        adminSession().set(model);
    }

    /**
     * 管理者DTO を初期化する
     */
    public void reset() {
        adminSession().reset();
    }

    /**
     * 実行不可アクションパスリスト
     *
     * @param inaccessibleActionPaths 実行不可アクションパスリスト
     */
    public void setInaccessibleActionPaths(List<String> inaccessibleActionPaths) {
        adminSession().setInaccessibleActionPaths(inaccessibleActionPaths);
    }

    /**
     * 引数のアクションパスが利用可能な場合は true を返却する
     *
     * @param actionPath アクションパス
     * @return {@code true} 利用可能
     */
    public boolean canAccess(String actionPath) {
        return adminSession().canAccess(actionPath);
    }

    /**
     * IDを返却する<br>
     * データベース更新時などの created_id に使用する
     *
     * @return ID
     */
    public String getId() {
        return adminSession().getId();
    }

    /**
     * ログインしているかどうか
     *
     * @return {@code true} ログインしている
     */
    public boolean isLogin() {
        return adminSession().isLogin();
    }

    /**
     * 管理者ログインの場合は true を返却する
     *
     * @return {@code true} 管理者ログイン
     */
    public boolean isAdminLogin() {
        return adminSession().isAdminLogin();
    }

    /**
     * 管理者セッションをルックアップする
     *
     * @return 管理者セッション
     */
    @Lookup("adminSession")
    public AdminSession adminSession() {
        return null;
    }
}
