package com.ibooks.common.database.repository;

import com.ibooks.common.model.ReviewModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * レビューレポジトリ
 */
@Repository
public interface ReviewRepository {
    /**
     * 引数のレビューIDのレビュー情報を取得する
     *
     * @param reviewId レビューID
     * @param bookId   ブックID
     * @return レビューモデル
     */
    Optional<ReviewModel> selectById(Long reviewId, Long bookId);

    /**
     * 引数のブックIDを条件に指定の評価値をカウントする
     *
     * @param bookId ブックID
     * @param kind   取得対象の評価値（0 ~ 5)
     * @return 評価値のカウント数
     */
    Integer countByBookId(Long bookId, Integer kind);

    /**
     * 引数のレビュー情報を更新する
     *
     * @param model      レビューモデル
     * @param registerId 登録者ID
     * @return レビューモデル
     */
    ReviewModel store(ReviewModel model, String registerId);

    /**
     * 引数のレビュー情報を削除する
     *
     * @param model レビューモデル
     * @return 削除件数
     */
    int delete(ReviewModel model);
}
