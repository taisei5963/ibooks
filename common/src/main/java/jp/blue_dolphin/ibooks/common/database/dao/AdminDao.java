package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.Admin;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 管理者DAO
 */
@ConfigAutowireable
@Dao
public interface AdminDao {
    /**
     * 引数の管理者IDを条件に取得する
     *
     * @param adminId 管理者ID
     * @return 管理者エンティティ
     */
    @Select
    Admin selectById(Long adminId);

    /**
     * 引数のログインIDを条件に取得する
     *
     * @param loginId ログインID
     * @return 管理者エンティティリスト
     */
    @Select
    List<Admin> selectByLoginId(String loginId);

    /**
     * 管理者情報を全件取得する
     *
     * @param orderBy ソート順
     * @param options セレクトオプション
     * @return 管理者エンティティリスト
     */
    @Select
    List<Admin> selectAll(String orderBy, SelectOptions options);

    /**
     * 登録
     *
     * @param entity 管理者エンティティ
     * @return 実行結果
     */
    @Insert
    Result<Admin> insert(Admin entity);

    /**
     * 更新
     *
     * @param entity 管理者エンティティ
     * @return 実行結果
     */
    @Update
    Result<Admin> update(Admin entity);

    /**
     * 削除
     *
     * @param entity 管理者エンティティ
     * @return 実行結果
     */
    @Delete
    Result<Admin> delete(Admin entity);

    /**
     * 引数の管理者IDのレコードを論理削除する
     *
     * @param adminId    管理者ID
     * @param now        現在時刻
     * @param registerId 削除者ID
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deleteById(Long adminId, LocalDateTime now, String registerId);
}
