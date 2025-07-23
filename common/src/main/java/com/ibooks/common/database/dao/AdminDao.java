package com.ibooks.common.database.dao;

import com.ibooks.common.database.entity.Admin;
import com.ibooks.common.dto.IdAndCodeAndName;
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
     * 引数のソート順で管理者一覧を取得する
     *
     * @param orderBy ソート順
     * @param options 検索オプション
     * @return 管理者エンティティリスト
     */
    @Select
    List<Admin> selectAll(String orderBy, SelectOptions options);

    /**
     * IDを条件に取得する
     *
     * @param adminId 管理者ID
     * @return 管理者エンティティ
     */
    @Select
    Admin selectById(Long adminId);

    /**
     * ログインIDを条件に取得する
     *
     * @param loginId ログインID
     * @return 管理者エンティティリスト
     */
    @Select
    List<Admin> selectByLoginId(String loginId);

    /**
     * 管理者IDとコード、管理者名のリストを返却する
     *
     * @return 管理者IDと管理者名のリスト
     */
    @Select
    List<IdAndCodeAndName> selectIdAndCodeAndName();

    /**
     * 登録する
     *
     * @param entity 管理者エンティティ
     * @return 登録結果
     */
    @Insert
    Result<Admin> insert(Admin entity);

    /**
     * 更新する
     *
     * @param entity 管理者エンティティ
     * @return 更新結果
     */
    @Update
    Result<Admin> update(Admin entity);

    /**
     * 引数の管理者IDのレコードを論理削除する
     *
     * @param adminId    管理者ID
     * @param now        現在時刻
     * @param registerId 登録者ID
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deleteById(Long adminId, LocalDateTime now, String registerId);
}
