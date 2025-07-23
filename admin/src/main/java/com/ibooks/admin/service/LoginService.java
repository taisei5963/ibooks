package com.ibooks.admin.service;

import com.ibooks.admin.config.LoginConfig;
import com.ibooks.admin.dto.AdminDto;
import com.ibooks.admin.request.LoginForm;
import com.ibooks.common.database.repository.AdminRepository;
import com.ibooks.common.exception.LoginLockException;
import com.ibooks.common.model.AdminModel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * ログインサービス
 */
@AllArgsConstructor
@Service
public class LoginService {
    /** 管理者リポジトリ */
    private AdminRepository adminRepository;
    /** ログイン設定 */
    private LoginConfig loginConfig;
    /** パスワードエンコーダー */
    private PasswordEncoder passwordEncoder;
    /** 管理者DTO */
    private AdminDto adminDto;

    /**
     * ログイン中の管理者を返却する
     *
     * @return 管理者モデル
     */
    public Optional<AdminModel> getAdmin() {
        return adminRepository.selectByLoginId(adminDto.getLoginId());
    }

    /**
     * 引数のログインフォームから管理者を取得する
     *
     * @param form ログインフォーム
     * @return 管理者モデル
     * @throws LoginLockException ログインロックが発生している場合にスローされる
     */
    public Optional<AdminModel> login(LoginForm form) throws LoginLockException, InterruptedException {
        Optional<AdminModel> opt = adminRepository.selectByLoginId(form.getLoginId());
        if (opt.isEmpty()) {
            return opt;
        }
        AdminModel admin = opt.get();
        Integer limit = loginConfig.getFailureLimit();
        Integer lockSec = loginConfig.getFailureLockSec();
        if (admin.getLoginFailureCount() >= limit) {
            LocalDateTime lockEnd = admin.getLoginFailureDate().plusSeconds(lockSec);
            LocalDateTime now = LocalDateTime.now();
            if (lockEnd.isAfter(now)) {
                Duration duration = Duration.between(now, lockEnd);
                throw new LoginLockException(duration.getSeconds());
            }
            admin = adminRepository.resetLoginFailureCount(admin);
        }
        if (!passwordEncoder.matches(form.getPassword(), admin.getPassword())) {
            adminRepository.inCreaseLoginFailureCount(admin);
            return Optional.empty();
        }
        return Optional.of(admin);
    }

    /**
     * ログイン失敗回数を増やす
     *
     * @return 管理者モデル
     * @throws LoginLockException ログインロックが発生している場合にスローされる
     */
    public AdminModel inCreaseLoginFailureCount() throws LoginLockException {
        AdminModel admin = adminRepository.selectByLoginId(adminDto.getLoginId()).orElse(null);
        if (Objects.isNull(admin)) {
            return null;
        }
        Integer limit = loginConfig.getFailureLimit();
        ;
        Integer lockSec = loginConfig.getFailureLockSec();
        if (admin.getLoginFailureCount() <= limit) {
            LocalDateTime lockEnd = admin.getLoginFailureDate().plusSeconds(lockSec);
            LocalDateTime now = LocalDateTime.now();
            if (lockEnd.isAfter(now)) {
                Duration duration = Duration.between(now, lockEnd);
                throw new LoginLockException(duration.getSeconds());
            }
            admin = adminRepository.resetLoginFailureCount(admin);
        }
        return adminRepository.inCreaseLoginFailureCount(admin);
    }

    /**
     * 引数の管理者のログイン失敗回数をリセットする
     *
     * @param model 管理者モデル
     * @return 管理者モデル
     */
    public AdminModel resetLoginFailureCount(AdminModel model) {
        return adminRepository.resetLoginFailureCount(model);
    }

    /**
     * 引数の管理者の最終ログイン日時を更新する
     *
     * @param model 管理者モデル
     * @return 管理者モデル
     */
    public AdminModel updateLastLoginDate(AdminModel model) {
        return adminRepository.updateLastLoginDate(model);
    }
}
