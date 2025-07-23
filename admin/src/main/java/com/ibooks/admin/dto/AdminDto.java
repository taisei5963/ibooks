package com.ibooks.admin.dto;

import com.ibooks.common.constants.SiteType;
import com.ibooks.common.dto.Account;
import com.ibooks.common.model.AdminModel;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 管理者DTO<br>
 * セッションスコープの管理者セッションをプロキシする
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
     * アカウントステータスを取得する
     *
     * @return アカウントステータス
     */
    public String getAccountStatus() {
        return adminSession().getAccountStatus();
    }

    /**
     * ログインIDを取得する
     *
     * @return ログインID
     */
    public String getLoginId() {
        return adminSession().getLoginId();
    }

    /**
     * 機能リストを取得する
     *
     * @return 機能リスト
     */
    public List<String> getFunctionList() {
        return adminSession().getFunctionList();
    }

    /**
     * 実行できないアクションパスリストを取得する
     *
     * @return 実行できないアクションパスリスト
     */
    public List<String> getInAccessibleActionPathList() {
        return adminSession().getInAccessibleActionPathList();
    }

    /**
     * 引数の管理者モデルをセットする
     *
     * @param model 管理者モデル
     */
    public void set(AdminModel model) {
        adminSession().set(model);
    }

    /**
     * 管理者DTOを初期化する
     */
    public void init() {
        adminSession().init();
    }

    /**
     * 実行できないアクションパスリストをセットする
     *
     * @param inAccessibleActionPathList 実行できないアクションパスリスト
     */
    public void setInAccessibleActionPathList(List<String> inAccessibleActionPathList) {
        adminSession().setInAccessibleActionPathList(inAccessibleActionPathList);
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
     * データベースの更新等の registerId に使用する
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
     * 管理者セッションをルックアップして返却する
     *
     * @return 管理者セッション
     */
    @Lookup("adminSession")
    public AdminSession adminSession() {
        return null;
    }
}
