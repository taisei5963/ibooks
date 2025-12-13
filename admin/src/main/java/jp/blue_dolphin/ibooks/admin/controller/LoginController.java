package jp.blue_dolphin.ibooks.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.blue_dolphin.ibooks.admin.config.LoginConfig;
import jp.blue_dolphin.ibooks.admin.dto.AdminDto;
import jp.blue_dolphin.ibooks.admin.request.LoginForm;
import jp.blue_dolphin.ibooks.admin.service.ActionRoleService;
import jp.blue_dolphin.ibooks.admin.service.AdminService;
import jp.blue_dolphin.ibooks.admin.service.LoginService;
import jp.blue_dolphin.ibooks.common.annotation.UnLogin;
import jp.blue_dolphin.ibooks.common.exception.LoginLockException;
import jp.blue_dolphin.ibooks.common.model.AdminModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
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
     * @param model テンプレートモデル
     * @return テンプレートパス
     */
    @UnLogin
    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        if (adminDto.isLogin()) {
            return "redirect:book/index";
        }
        return "login/login";
    }

    @UnLogin
    @PostMapping("login")
    public String login(LoginForm loginForm, Model model, HttpSession session,
                        HttpServletRequest req)
            throws InterruptedException {
        if (adminDto.isLogin()) {
            return "redirect:book/index";
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
        return "redirect:book/index";
    }
}
