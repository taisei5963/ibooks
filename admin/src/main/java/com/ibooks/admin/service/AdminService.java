package com.ibooks.admin.service;

import com.ibooks.admin.config.AdminConfig;
import com.ibooks.admin.dto.AdminDto;
import com.ibooks.admin.request.AdminForm;
import com.ibooks.common.constants.AccountStatus;
import com.ibooks.common.constants.LoginFlg;
import com.ibooks.common.database.repository.AdminRepository;
import com.ibooks.common.dto.IdAndCodeAndName;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.AdminModel;
import com.ibooks.common.request.SearchForm;
import com.ibooks.common.service.MessageService;
import com.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 管理者サービス
 */
@AllArgsConstructor
@Getter
@Service
public class AdminService {
    /** 管理者リポジトリ */
    private AdminRepository adminRepository;
    /** 管理サイト設定 */
    private AdminConfig adminConfig;
    /** パスワードエンコーダー */
    private PasswordEncoder passwordEncoder;
    /** 管理者DTO */
    private AdminDto adminDto;
    /** メッセージサービス */
    private MessageService messageService;

    /**
     * 引数の管理者IDの管理者を取得する
     *
     * @param adminId 管理者ID
     * @return 管理者モデル
     */
    public Optional<AdminModel> get(Long adminId) {
        return adminRepository.selectById(adminId);
    }

    /**
     * 引数の検索条件で管理者を全件取得する
     *
     * @param searchForm 検索フォーム
     * @param pageable   ページャブル
     * @return 検索結果
     */
    @Transactional
    public SearchResult<AdminModel> getAll(SearchForm searchForm, Pageable pageable) {
        return adminRepository.selectAll(searchForm.getOrderBy(), searchForm.getSelectOptions(pageable));
    }

    /**
     * 引数のログインIDから管理者を取得する
     *
     * @param loginId ログインID
     * @return 取得対象管理者モデル
     */
    public Optional<AdminModel> selectByLoginId(String loginId) {
        return adminRepository.selectByLoginId(loginId);
    }

    /**
     * 引数の管理者モデルを登録する
     *
     * @param adminModel 管理者モデル
     * @return 管理者モデル
     */
    public AdminModel store(AdminModel adminModel) {
        return adminRepository.store(adminModel, adminDto.getId());
    }

    /**
     * 引数の管理者フォームから管理者モデルを作成して登録する
     *
     * @param form 管理者フォーム
     * @return 管理者モデル
     */
    public AdminModel create(AdminForm form) {
        AdminModel model = createModelOf(form);
        return adminRepository.store(model, adminDto.getId());
    }

    /**
     * 引数の管理者モデルを管理者フォームで上書きして登録する
     *
     * @param model 管理者モデル
     * @param form  管理者フォーム
     * @return 管理者モデル
     */
    public AdminModel update(AdminModel model, AdminForm form) {
        AdminModel.AdminModelBuilder builder = model.toBuilder();
        builder.name(form.getName());
        if (!Strings.isEmpty(form.getPassword())) {
            String password = passwordEncoder.encode(form.getPassword());
            builder.password(password);
        }
        builder.ver(form.getVer());
        if (!adminDto.getId().equals(form.getLoginId()) && adminDto.getAccountStatus()
                .equals(AccountStatus.ACTIVE.getValue())) {
            builder.accountStatus(AccountStatus.ACTIVE);
        }
        builder.loginFlg(LoginFlg.ON);
        AdminModel admin = builder.build();
        return adminRepository.store(admin, adminDto.getId());
    }

    /**
     * 管理者を論理削除する
     *
     * @param model 管理者モデル
     * @return 削除結果
     */
    public int delete(AdminModel model) {
        return adminRepository.deleteById(model.getAdminId(), adminDto.getId());
    }

    /**
     * 管理者パスワードのバリデーションを行う
     *
     * @param password パスワード
     * @return {@code true} OK
     */
    public boolean validatePassword(String password) {
        int minLength = this.adminConfig.getAdminPasswordMinLength();
        boolean checkMixedChars = this.adminConfig.enableAdminPasswordCheckMixedChars();
        if (Strings.isEmpty(password) || password.length() < minLength) {
            return false;
        }
        if (checkMixedChars) {
            if (!password.matches(".*[0-9].*")) {
                return false;
            } else if (!password.matches(".*[0-9].*")) {
                return false;
            }
            return password.matches(".*[A-Z].*");
        }
        return true;
    }

    /**
     * パスワードを更新する
     *
     * @param admin       管理者モデル
     * @param newPassword 新しいパスワード
     * @param ver         バージョン
     * @return 管理者モデル
     */
    public AdminModel updatePassword(AdminModel admin, String newPassword, Integer ver) {
        String password = passwordEncoder.encode(newPassword);
        AdminModel setModel = admin.toBuilder()
                .password(password)
                .ver(ver)
                .build();
        return adminRepository.store(setModel, adminDto.getId());

    }

    /**
     * 管理者IDとコード、管理者名のリストを取得する
     *
     * @return 管理者IDとコード、管理者名のリスト
     */
    public List<IdAndCodeAndName> selectIdAndCodeAndName() {
        return adminRepository.selectIdAndCodeAndNames();
    }

    /**
     * 引数の管理者フォームから管理者モデルを作成する
     *
     * @param form 管理者フォーム
     * @return 管理者モデル
     */
    private AdminModel createModelOf(AdminForm form) {
        String password = passwordEncoder.encode(form.getPassword());
        return AdminModel.builder()
                .loginId(form.getLoginId())
                .name(form.getName())
                .password(password)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
    }
}
