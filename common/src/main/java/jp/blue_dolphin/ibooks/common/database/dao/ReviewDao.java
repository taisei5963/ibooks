package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.Review;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.math.BigDecimal;

/**
 * レビューDAO
 */
@ConfigAutowireable
@Dao
public interface ReviewDao {
    /**
     * 引数のレビューIDとブックIDを条件に取得する
     *
     * @param reviewId レビューID
     * @param bookId   ブックID
     * @return レビューエンティティ
     */
    @Select
    Review selectByIdAndBookId(Long reviewId, Long bookId);

    /**
     * 引数のブックIDと種別に応じたレビュー評価値をカウントする
     *
     * @param bookId ブックID
     * @param kind   種別
     * @return レビュー評価値のカウント
     */
    @Select
    int countRatingByBookId(Long bookId, int kind);

    /**
     * 引数のブックIDを条件にレビュー評価値の平均値を取得する
     *
     * @param bookId ブックID
     * @return レビュー評価値の平均値
     */
    @Select
    BigDecimal avgRatingByBookId(Long bookId);

    /**
     * 登録する
     *
     * @param entity レビューエンティティ
     * @return 登録結果
     */
    @Insert
    Result<Review> insert(Review entity);

    /**
     * 更新する
     *
     * @param entity レビューエンティティ
     * @return 更新結果
     */
    @Update
    Result<Review> update(Review entity);

    /**
     * 削除する
     *
     * @param entity レビューエンティティ
     * @return 削除結果
     */
    @Delete
    Result<Review> delete(Review entity);
}
