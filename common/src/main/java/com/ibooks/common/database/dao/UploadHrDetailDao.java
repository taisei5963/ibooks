package com.ibooks.common.database.dao;

import com.ibooks.common.database.entity.UploadHrDetail;
import org.seasar.doma.BatchInsert;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.BatchResult;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SqlLogType;

import java.util.List;

/**
 * アップロード履歴詳細DAO
 */
@ConfigAutowireable
@Dao
public interface UploadHrDetailDao {

    /**
     * ID を条件に取得
     *
     * @param uploadHrDetailId ID
     * @return アップロード履歴詳細エンティティ
     */
    @Select
    UploadHrDetail selectById(Long uploadHrDetailId);

    /**
     * 登録.
     *
     * @param entity アップロード履歴エンティティ
     * @return affected row
     */
    @Insert
    Result<UploadHrDetail> insert(UploadHrDetail entity);

    /**
     * バッチ登録
     *
     * @param entities エンティティリスト
     * @return バッチ登録結果
     */
    @BatchInsert(batchSize = 100, sqlLog = SqlLogType.NONE)
    BatchResult<UploadHrDetail> insert(List<UploadHrDetail> entities);

    /**
     * 更新.
     *
     * @param entity アップロード履歴エンティティ
     * @return affected row
     */
    @Update
    Result<UploadHrDetail> update(UploadHrDetail entity);

    /**
     * 削除.
     *
     * @param entity アップロード履歴エンティティ
     * @return affected row
     */
    @Delete
    Result<UploadHrDetail> delete(UploadHrDetail entity);

    /**
     * 引数のアップロード履歴IDリストのレコードを物理削除する
     *
     * @param uploadHistIdList 削除対象アップロード履歴IDリスト
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deletePhysicalDetail(List<Long> uploadHistIdList);
}
