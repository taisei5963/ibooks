package jp.blue_dolphin.ibooks.admin.service;

import jp.blue_dolphin.ibooks.admin.config.AdminConfig;
import jp.blue_dolphin.ibooks.admin.dto.AdminDto;
import jp.blue_dolphin.ibooks.common.database.repository.AdminRepository;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.AdminModel;
import jp.blue_dolphin.ibooks.common.request.SearchForm;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 管理者サービス
 */
@AllArgsConstructor
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
    public Optional<AdminModel> selectById(Long adminId) {
        return adminRepository.selectById(adminId);
    }

    /**
     * 引数のログインIDの管理者モデルを取得する
     *
     * @param loginId ログインID
     * @return 管理者モデル
     */
    public Optional<AdminModel> selectByLoginId(String loginId) {
        return adminRepository.selectByLoginId(loginId);
    }

    /**
     * 管理者を全権取得する
     *
     * @param form     検索フォーム
     * @param pageable ページャブル
     * @return 全権管理者モデル
     */
    public SearchResult<AdminModel> selectAll(SearchForm form, Pageable pageable) {
        return adminRepository.selectAll(form.getOrderBy(), form.getSelectOptions(pageable));
    }

    /**
     * 管理者を論理削除する
     *
     * @param model 管理者モデル
     * @return 削除件数
     */
    public int delete(AdminModel model) {
        return adminRepository.deleteById(model.getAdminId(), adminDto.getId());
    }
}
