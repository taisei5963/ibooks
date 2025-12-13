package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.AdminModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 管理者リポジトリ
 */
@Repository
public interface AdminRepository {
    /**
     * 引数の管理者IDを条件に取得する
     *
     * @param adminId 管理者ID
     * @return 管理者エンティティ
     */
    Optional<AdminModel> selectById(Long adminId);

    /**
     * 引数のログインIDを条件に管理者モデルを取得する
     *
     * @param loginId ログインID
     * @return 管理者モデル
     */
    Optional<AdminModel> selectByLoginId(String loginId);

    /**
     * 引数の順で管理者一覧を取得する
     *
     * @param orderBy ソート順
     * @param options セレクトオプション
     * @return 管理者モデル
     */
    SearchResult<AdminModel> selectAll(String orderBy, SelectOptions options);

    /**
     * 引数の管理者を論理削除する
     *
     * @param adminId   削除対象の管理者ID
     * @param createdId 削除者ID
     * @return 削除結果
     */
    int deleteById(Long adminId, String createdId);

    /**
     * 引数の管理者を更新する
     *
     * @param adminModel 管理者モデル
     * @param createdId  更新者ID
     * @return 管理者モデル
     */
    AdminModel store(AdminModel adminModel, String createdId);

    /**
     * 最終ログイン日時を更新する
     *
     * @param adminModel 管理者モデル
     * @return 管理者モデル
     */
    AdminModel updateLastLoginDate(AdminModel adminModel);

    /**
     * ログイン失敗回数を増やす
     *
     * @param adminModel 管理者モデル
     * @return 管理者モデル
     */
    AdminModel increaseLoginFailureCount(AdminModel adminModel);

    /**
     * ログイン失敗回数をリセットする
     *
     * @param adminModel 管理者モデル
     * @return 管理者モデル
     */
    AdminModel resetLoginFailureCount(AdminModel adminModel);
}
