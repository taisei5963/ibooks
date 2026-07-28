package com.techcof.ITive.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.techcof.ITive.admin.config.LoginConfig;
import com.techcof.ITive.admin.dto.AdminDto;
import com.techcof.ITive.admin.request.LoginForm;
import com.techcof.ITive.admin.service.ActionRoleService;
import com.techcof.ITive.admin.service.AdminService;
import com.techcof.ITive.admin.service.LoginService;
import com.techcof.ITive.common.annotation.UnLogin;
import com.techcof.ITive.common.exception.LoginLockException;
import com.techcof.ITive.common.model.AdminModel;
import com.techcof.ITive.common.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * ログインコントローラー
 */
@AllArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {
    /** ログインサービス */
    private LoginService loginService;
    /** 管理者サービス */
    private AdminService adminService;
    /** アクションロールサービス */
    private ActionRoleService actionRoleService;
    /** メッセージサービス */
    private MessageService messageService;
    /** ログイン設定 */
    private LoginConfig loginConfig;
    /** 管理者DTO */
    private AdminDto adminDto;

    /**
     * ログイン画面
     *
     * @return テンプレートパス
     */
    @UnLogin
    @GetMapping(value = {"", "/"})
    public String index() {
        if (adminDto.isLogin()) {
            return "redirect:/home";
        }
        return "login/login";
    }

    /**
     * ログインする
     *
     * @param loginForm ログインフォーム
     * @param model     モデル
     * @param session   セッション
     * @param req       リクエスト
     * @return テンプレートパス
     */
    @UnLogin
    @PostMapping("login")
    public String login(LoginForm loginForm, Model model, HttpSession session,
                        HttpServletRequest req) throws InterruptedException {
        if (adminDto.isLogin()) {
            return "redirect:/home";
        }
        Optional<AdminModel> opt;
        try {
            opt = loginService.login(loginForm);
        } catch (LoginLockException e) {
            model.addAttribute("loginError", e.getMessage());
            return "login/login";
        }
        if (opt.isEmpty()) {
            model.addAttribute("loginError",
                    messageService.getMessage("errors.login.invalidIdPass"));
            return "login/login";
        }
        AdminModel admin = opt.get();
        admin = loginService.resetLoginFailureCount(admin);
        session.invalidate();
        req.getSession(true);
        adminDto.set(admin);
        if (!loginConfig.enableTowFactorAuth()) {
            loginService.updateLastLoginDate(admin);
            adminDto.setInaccessibleActionPaths(
                    actionRoleService.getInaccessibleActionPaths(adminDto.getPrivilegeId()));
        }
        return "redirect:/home";
    }

    /**
     * ログアウトする
     *
     * @param session セッション
     * @return テンプレートパス
     */
    @PostMapping("logout")
    public String logout(HttpSession session) {
        // セッション破棄
        session.invalidate();
        // DTOリセット
        adminDto.reset();
        // ログイン画面へリダイレクト
        return "redirect:/login";
    }
}
