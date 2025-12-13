package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.ActionRole;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;

/**
 * アクションロールDAO
 */
@ConfigAutowireable
@Dao
public interface ActionRoleDao {
    /**
     * 引数のアクションロールIDを条件に取得する
     *
     * @param actionRoleId アクションロールID
     * @return アクションロールエンティティ
     */
    @Select
    ActionRole selectById(Long actionRoleId);

    /**
     * 引数の権限IDで実行不可なアクションロールを返却する
     *
     * @param privilegeId 権限ID
     * @return アクションロール
     */
    @Select
    List<ActionRole> selectInaccessibleByPrivilegeId(Long privilegeId);

    /**
     * 引数の権限IDのアクションロールを返却する
     *
     * @param privilegeId 権限ID
     * @return アクションロール
     */
    @Select
    List<ActionRole> selectByPrivilegeId(Long privilegeId);

    /**
     * 登録する
     *
     * @param entity アクションロールエンティティ
     * @return 実行結果
     */
    @Insert
    Result<ActionRole> insert(ActionRole entity);

    /**
     * 更新する
     *
     * @param entity アクションロールエンティティ
     * @return 実行結果
     */
    @Update
    Result<ActionRole> update(ActionRole entity);

    /**
     * 削除する
     *
     * @param entity アクションロールエンティティ
     * @return 実行結果
     */
    @Delete
    Result<ActionRole> delete(ActionRole entity);
}
