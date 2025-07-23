package com.ibooks.common.database.dao;

import com.ibooks.common.constants.SequenceType;
import com.ibooks.common.database.entity.SequenceManager;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.time.LocalDateTime;
import java.util.List;

/**
 * シーケンスDAO
 */
@ConfigAutowireable
@Dao
public interface SequenceManagerDao {
    /**
     * 引数のシーケンスマネージャーIDのシーケンスマネージャーエンティティ
     *
     * @param sequenceManagerId シーケンスマネージャーID
     * @return シーケンスマネージャーエンティティ
     */
    @Select
    SequenceManager selectById(Long sequenceManagerId);

    /**
     * 引数のシーケンスタイプのシーケンスマネージャーエンティティを取得する
     *
     * @param sequenceType シーケンスタイプ
     * @return シーケンスマネージャーエンティティ
     */
    @Select
    List<SequenceManager> selectBySequenceType(SequenceType sequenceType);

    /**
     * 登録
     *
     * @param entity シーケンスマネージャーエンティティ
     * @return 登録処理結果
     */
    @Insert
    Result<SequenceManager> insert(SequenceManager entity);

    /**
     * 更新
     *
     * @param entity シーケンスマネージャーエンティティ
     * @return 更新処理結果
     */
    @Update
    Result<SequenceManager> update(SequenceManager entity);

    /**
     * 引数のシーケンスマネージャーIDのシーケンス番号を更新する
     *
     * @param sequenceManagerId シーケンスマネージャーID
     * @param nextSequence      次値のシーケンス
     * @param now               現在日時
     * @param nowSequence       現在値のシーケンス
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateSequence(Long sequenceManagerId, Integer nextSequence, LocalDateTime now, Integer nowSequence);

    /**
     * 削除
     *
     * @param entity シーケンスマネージャーエンティティ
     * @return 削除処理結果
     */
    @Delete
    Result<SequenceManager> delete(SequenceManager entity);
}
