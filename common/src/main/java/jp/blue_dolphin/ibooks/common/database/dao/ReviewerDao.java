package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.Reviewer;
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
     * 引数のレビュワーIDを条件に取得する
     *
     * @param reviewerId レビュワーID
     * @return レビュワーエンティティ
     */
    @Select
    Reviewer selectById(Long reviewerId);

    /**
     * 引数のログインIDを条件に取得する
     *
     * @param loginId ログインID
     * @return レビュワーエンティティリスト
     */
    @Select
    List<Reviewer> selectByLoginId(String loginId);

    /**
     * レビュワー情報を全件取得する
     *
     * @param orderBy ソート順
     * @return レビュワーエンティティリスト
     */
    @Select
    List<Reviewer> selectAll(String orderBy);

    /**
     * 登録
     *
     * @param entity レビュワーエンティティ
     * @return 実行結果
     */
    @Insert
    Result<Reviewer> insert(Reviewer entity);

    /**
     * 更新
     *
     * @param entity レビュワーエンティティ
     * @return 実行結果
     */
    @Update
    Result<Reviewer> update(Reviewer entity);

    /**
     * 削除
     *
     * @param entity レビュワーエンティティ
     * @return 実行結果
     */
    @Delete
    Result<Reviewer> delete(Reviewer entity);

    /**
     * 引数の管理者IDのレコードを論理削除する
     *
     * @param reviewerId レビュワーID
     * @param now        現在時刻
     * @param registerId 削除者ID
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deleteById(Long reviewerId, LocalDateTime now, String registerId);
}
