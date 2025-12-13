package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.UploadHistDetail;
import org.seasar.doma.BatchInsert;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.BatchResult;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.SqlLogType;

import java.util.List;

/**
 * アップロード履歴詳細DAO
 */
@ConfigAutowireable
@Dao
public interface UploadHistDetailDao {
    /**
     * アップロード履歴詳細IDを条件に取得する
     *
     * @param uploadHistDetailId アップロード履歴詳細ID
     * @return アップロード履歴詳細エンティティ
     */
    @Select
    UploadHistDetail selectById(Long uploadHistDetailId);

    /**
     * 引数の検索条件で取得する
     *
     * @param uploadHistId アップロード履歴ID
     * @param orderBy      ソート順
     * @param options      検索オプション
     * @return 検索結果
     */
    @Select
    List<UploadHistDetail> selectByUploadHistId(Long uploadHistId, String orderBy,
                                                SelectOptions options);

    /**
     * 登録する
     *
     * @param entity アップロード履歴詳細エンティティ
     * @return 実行結果
     */
    @Insert
    Result<UploadHistDetail> insert(UploadHistDetail entity);

    /**
     * バッチ登録
     *
     * @param entities アップロード履歴詳細エンティティリスト
     * @return バッチ登録結果
     */
    @BatchInsert(batchSize = 100, sqlLog = SqlLogType.NONE)
    BatchResult<UploadHistDetail> insert(List<UploadHistDetail> entities);

    /**
     * 更新する
     *
     * @param entity アップロード履歴詳細エンティティ
     * @return 実行結果
     */
    @Update
    Result<UploadHistDetail> update(UploadHistDetail entity);

    /**
     * 削除する
     *
     * @param entity アップロード履歴詳細エンティティ
     * @return 実行結果
     */
    @Delete
    Result<UploadHistDetail> delete(UploadHistDetail entity);

    /**
     * アップロード履歴詳細を物理削除する
     *
     * @param uploadHistIds 削除対象アップロード履歴IDリスト
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deletePhysicalDetail(List<Long> uploadHistIds);
}
