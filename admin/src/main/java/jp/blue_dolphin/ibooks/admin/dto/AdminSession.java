package jp.blue_dolphin.ibooks.admin.dto;

import jp.blue_dolphin.ibooks.common.dto.UserInfo;
import jp.blue_dolphin.ibooks.common.model.AdminModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@SessionScope
public class AdminSession implements Serializable, UserInfo {
    @Serial
    private static final long serialVersionUID = 8109378978485822975L;

    /** 管理者ID */
    private Long adminId = null;
    /** 管理者名 */
    private String name = null;
    /** ログインID */
    private String loginId = null;
    /** 権限ID */
    private Long privilegeId = null;
    /** 機能リスト */
    private List<String> functions = null;
    /** 実行不可アクションパスリスト */
    @Setter
    private List<String> inaccessibleActionPaths = null;

    /**
     * 引数の管理者モデルの情報を設定する
     *
     * @param model 管理者モデル
     */
    public void set(AdminModel model) {
        this.adminId = model.getAdminId();
        this.name = model.getName();
        this.privilegeId = model.getPrivilegeId();
        this.loginId = model.getLoginId();
        this.functions = new ArrayList<>();
        this.inaccessibleActionPaths = new ArrayList<>();
    }

    /**
     * 管理者DTOを初期化する
     */
    public void reset() {
        this.adminId = null;
        this.name = null;
        this.loginId = null;
        this.privilegeId = null;
        this.functions = null;
        this.inaccessibleActionPaths = null;
    }

    /**
     * 引数のアクションパスが利用可能な場合は true を返却する
     *
     * @param actionPath アクションパス
     * @return {@code true} 利用可能
     */
    public boolean canAccess(String actionPath) {
        if (this.inaccessibleActionPaths == null) {
            return false;
        }
        return !this.inaccessibleActionPaths.contains(actionPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return loginId;
    }

    /**
     * ログインしているかどうか
     *
     * @return {@code true} ログインしている
     */
    public boolean isLogin() {
        return getId() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdminLogin() {
        return true;
    }
}
