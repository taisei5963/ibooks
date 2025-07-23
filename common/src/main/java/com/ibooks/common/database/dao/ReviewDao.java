package com.ibooks.common.database.dao;

import com.ibooks.common.database.entity.Review;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

/**
 * レビューDAO
 */
@ConfigAutowireable
@Dao
public interface ReviewDao {
    /**
     * レビューIDを条件に取得する
     *
     * @param reviewId レビューID
     * @param bookId   ブックID
     * @return レビューエンティティ
     */
    @Select
    Review selectById(Long reviewId, Long bookId);

    /**
     * ブックIDを条件に指定の評価値をカウントする<br>
     * 取得対象の評価値で 0 を指定した場合は全評価値をカウントする
     *
     * @param bookId ブックID
     * @param kind   取得対象の評価値（0 ~ 5）
     * @return 評価値のカウント
     */
    @Select
    Integer countByBookId(Long bookId, Integer kind);

    /**
     * レビューを登録する
     *
     * @param entity レビューエンティティ
     * @return 登録結果
     */
    @Insert
    Result<Review> insert(Review entity);

    /**
     * レビューを更新する
     *
     * @param entity レビューエンティティ
     * @return 更新結果
     */
    @Update
    Result<Review> update(Review entity);

    /**
     * レビューを削除する
     *
     * @param entity レビューエンティティ
     * @return 削除結果
     */
    @Delete
    Result<Review> delete(Review entity);
}
