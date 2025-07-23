package com.ibooks.admin.controller;

import com.ibooks.admin.config.LoginConfig;
import com.ibooks.admin.dto.AdminDto;
import com.ibooks.admin.request.LoginForm;
import com.ibooks.admin.service.AdminService;
import com.ibooks.admin.service.LoginService;
import com.ibooks.common.annotation.NotLogin;
import com.ibooks.common.exception.LoginLockException;
import com.ibooks.common.model.AdminModel;
import com.ibooks.common.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    /** メッセージサービス */
    private MessageService messageService;
    /** ログイン設定 */
    private LoginConfig loginConfig;
    /** 管理者DTO */
    private AdminDto adminDto;

    @NotLogin
    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        if (adminDto.isLogin()) {
            return "redirect:/";
        }
        return "login/login";
    }

    /**
     * ログインする
     *
     * @param loginForm ログインフォーム
     * @param model     テンプレートモデル
     * @param session   セッション
     * @param req       リクエスト
     * @return テンプレートパス
     */
    @NotLogin
    @PostMapping("login")
    public String login(LoginForm loginForm, Model model, HttpSession session, HttpServletRequest req)
            throws InterruptedException {
        if (adminDto.isLogin()) {
            return "redirect:/";
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
                    messageService.getMessage("errors.login.invalid"));
            return "login/login";
        }
        AdminModel admin = opt.get();
        admin = loginService.resetLoginFailureCount(admin);
        session.invalidate();
        req.getSession(true);
        adminDto.set(admin);
        return "redirect:/";
    }

    /**
     * ログアウトする
     *
     * @param session セッション
     * @param req     リクエスト
     * @return テンプレートパス
     */
    @GetMapping("logout")
    @NotLogin
    public String logout(HttpSession session, HttpServletRequest req) {
        session.invalidate();
        req.getSession();
        adminDto.init();
        return "redirect:/login";
    }
}
