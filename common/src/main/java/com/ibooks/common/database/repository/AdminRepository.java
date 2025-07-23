package com.ibooks.common.database.repository;

import com.ibooks.common.database.entity.Admin;
import com.ibooks.common.dto.IdAndCodeAndName;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.AdminModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 管理者レポジトリ
 */
@Repository
public interface AdminRepository {
    /**
     * 引数のソート順で管理者一覧を取得する
     *
     * @param orderBy オーダ句
     * @param options 検索オプション
     * @return 管理者モデル
     */
    SearchResult<AdminModel> selectAll(String orderBy, SelectOptions options);

    /**
     * 引数の管理者IDの管理者を取得する
     *
     * @param adminId 管理者ID
     * @return 管理者モデル
     */
    Optional<AdminModel> selectById(Long adminId);

    /**
     * 引数のログインIDの管理者を取得する
     *
     * @param loginId ログインID
     * @return 管理者モデル
     */
    Optional<AdminModel> selectByLoginId(String loginId);

    /**
     * 管理者IDとコード、管理者名のリストを取得する
     *
     * @return 管理者IDとコード、管理者名のリスト
     */
    List<IdAndCodeAndName> selectIdAndCodeAndNames();

    /**
     * 引数の管理者を更新する
     *
     * @param model      管理者モデル
     * @param registerId 登録者ID
     * @return 管理者モデル
     */
    AdminModel store(AdminModel model, String registerId);

    /**
     * 引数の管理者を削除する
     *
     * @param adminId    削除対象の管理者ID
     * @param registerId 登録者ID
     * @return 削除結果
     */
    int deleteById(Long adminId, String registerId);

    /**
     * 最終ログイン日時を更新する
     *
     * @param model 管理者モデル
     * @return 管理者モデル
     */
    AdminModel updateLastLoginDate(AdminModel model);

    /**
     * ログイン失敗回数を増やす
     *
     * @param model 管理者モデル
     * @return 管理者モデル
     */
    AdminModel inCreaseLoginFailureCount(AdminModel model);

    /**
     * ログイン失敗回数をリセットする
     *
     * @param model 管理者モデル
     * @return 管理者モデル
     */
    AdminModel resetLoginFailureCount(AdminModel model);
}
