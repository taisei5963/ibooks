package com.ibooks.common.database.dao;

import com.ibooks.common.database.entity.Reviewer;
import com.ibooks.common.dto.IdAndCodeAndName;
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
 * レビュワーDAO
 */
@ConfigAutowireable
@Dao
public interface ReviewerDao {
    /**
     * IDを条件に取得する
     *
     * @param reviewerId レビュワーID
     * @return レビュワーエンティティ
     */
    @Select
    Reviewer selectById(Long reviewerId);

    /**
     * ログインIDを条件に取得する
     *
     * @param loginId ログインID
     * @return レビュワーエンティティリスト
     */
    @Select
    List<Reviewer> selectByLoginId(String loginId);

    /**
     * レビュワーIDとコード、レビュワー名のリストを返却する
     *
     * @return レビュワーIDとレビュワー名のリスト
     */
    @Select
    List<IdAndCodeAndName> selectIdAndCodeAndName();

    /**
     * 登録する
     *
     * @param entity レビュワーエンティティ
     * @return 登録結果
     */
    @Insert
    Result<Reviewer> insert(Reviewer entity);

    /**
     * 更新する
     *
     * @param entity レビュワーエンティティ
     * @return 更新結果
     */
    @Update
    Result<Reviewer> update(Reviewer entity);

    /**
     * 引数のレビュワーIDのレコードを論理削除する
     *
     * @param reviewerId 管理者ID
     * @param now        現在時刻
     * @param registerId 登録者ID
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deleteById(Long reviewerId, LocalDateTime now, String registerId);
}
