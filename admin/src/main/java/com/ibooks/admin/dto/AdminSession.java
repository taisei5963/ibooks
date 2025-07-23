package com.ibooks.admin.dto;

import com.ibooks.common.dto.UserInfo;
import com.ibooks.common.model.AdminModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 管理者セッション
 */
@Component
@Getter
@SessionScope
public class AdminSession implements Serializable, UserInfo {
    @Serial
    private static final long serialVersionUID = -1804311363845909924L;

    /** 管理者ID */
    private Long adminId = null;
    /** 管理者名 */
    private String name = null;
    /** ログインID */
    private String loginId = null;
    /** アカウントステータス */
    private String accountStatus = null;
    /** 機能リスト */
    private List<String> functionList = null;
    /** 実行できないアクションパスリスト */
    @Setter
    private List<String> inAccessibleActionPathList = null;

    /**
     * 引数の管理者モデルをセットする
     *
     * @param model 管理者モデル
     */
    public void set(AdminModel model) {
        this.adminId = model.getAdminId();
        this.name = model.getName();
        this.loginId = model.getLoginId();
        this.accountStatus = model.getAccountStatus().getValue();
        this.functionList = new ArrayList<>();
        this.inAccessibleActionPathList = new ArrayList<>();
    }

    /**
     * 管理者DTOを初期化する
     */
    public void init() {
        this.adminId = null;
        this.name = null;
        this.loginId = null;
        this.accountStatus = null;
        this.functionList = null;
        this.inAccessibleActionPathList = null;
    }

    /**
     * 引数のアクションパスが利用可能な場合は true を返却する
     *
     * @param actionPath アクションパス
     * @return {@code true} 利用可能
     */
    public boolean canAccess(String actionPath) {
        if (this.inAccessibleActionPathList == null) {
            return false;
        }
        return !this.inAccessibleActionPathList.contains(actionPath);
    }

    /**
     * ログインしているかどうか
     *
     * @return {@code true} ログインしている
     */
    public boolean isLogin() {
        return !Objects.isNull(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return loginId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdminLogin() {
        return true;
    }
}
