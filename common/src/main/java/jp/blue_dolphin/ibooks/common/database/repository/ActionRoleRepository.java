package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.model.ActionRoleModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * アクションロールリポジトリ
 */
@Repository
public interface ActionRoleRepository {
    /**
     * 引数のアクションロールIDのアクションロールモデルを取得する
     *
     * @param actionRoleId アクションロールID
     * @return アクションロールモデル
     */
    Optional<ActionRoleModel> selectById(Long actionRoleId);

    /**
     * 引数の権限IDで実行不可なアクションロールリストを取得する
     *
     * @param privilegeId 権限ID
     * @return アクションロールリスト
     */
    List<ActionRoleModel> selectInaccessibleByPrivilegeId(Long privilegeId);

    /**
     * 引数の権限IDのアクションロールリストを取得する
     *
     * @param privilegeId 権限ID
     * @return アクションロールリスト
     */
    List<ActionRoleModel> selectByPrivilegeId(Long privilegeId);

    /**
     * 引数のアクションロールモデルを更新する
     *
     * @param model     アクションロールモデル
     * @param createdId 担当者ID
     * @return アクションロールモデル
     */
    ActionRoleModel store(ActionRoleModel model, String createdId);
}
