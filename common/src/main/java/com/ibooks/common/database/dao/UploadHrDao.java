package com.ibooks.common.database.dao;

import com.ibooks.common.constants.UploadType;
import com.ibooks.common.database.entity.UploadHr;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * アップロード履歴DAO
 */
@ConfigAutowireable
@Dao
public interface UploadHrDao {

    /**
     * ID を条件に取得
     *
     * @param uploadHrId ID
     * @return アップロード履歴エンティティ
     */
    @Select
    UploadHr selectById(Long uploadHrId);

    /**
     * 引数のアップロード種別の最新レコードを取得する
     *
     * @param uploadType アップロード種別
     * @return アップロード履歴エンティティ
     */
    @Select
    UploadHr selectLatestByUploadType(UploadType uploadType);

    /**
     * 引数の検索条件でアップロード履歴を取得する
     *
     * @param uploadType   アップロード種別
     * @param uploadStatus アップロードステータス
     * @param siteType     サイト種別
     * @param registerId   登録者ID
     * @param orderBy      ソート順
     * @param options      検索オプション
     * @return 検索結果
     */
    @Select
    List<UploadHr> selectByCond(String uploadType, String uploadStatus, String siteType, String registerId,
                                String orderBy, SelectOptions options);

    /**
     * 登録.
     *
     * @param entity アップロード履歴エンティティ
     * @return affected row
     */
    @Insert
    Result<UploadHr> insert(UploadHr entity);

    /**
     * 更新.
     *
     * @param entity アップロード履歴エンティティ
     * @return affected row
     */
    @Update
    Result<UploadHr> update(UploadHr entity);

    /**
     * 削除.
     *
     * @param entity アップロード履歴エンティティ
     * @return affected row
     */
    @Delete
    Result<UploadHr> delete(UploadHr entity);

    /**
     * 引数のアップロード履歴IDリストのレコードを物理削除する
     *
     * @param uploadHistIdList アップロード履歴IDリスト
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deletePhysical(List<Long> uploadHistIdList);
}
