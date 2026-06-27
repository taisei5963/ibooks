package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.model.ReviewModel;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * レビューリポジトリ
 */
@Repository
public interface ReviewRepository {
    /**
     * 引数のレビューIDとブックIDを条件にレビューモデルを取得する
     *
     * @param reviewId レビューID
     * @param bookId   ブックID
     * @return レビューモデルリスト
     */
    List<ReviewModel> selectByIdAndBookId(Long reviewId, Long bookId);

    /**
     * 引数のブックIDを条件にカウント対象の評価値をカウントする
     *
     * @param bookId ブックID
     * @param kind   カウント対象の評価値
     * @return カウント結果
     */
    int countRatingByBookId(Long bookId, int kind);

    /**
     * 引数のブックIDを条件に評価値の平均値を算出する
     *
     * @param bookId ブックID
     * @return 評価値の平均値
     */
    BigDecimal avgRatingByBookId(Long bookId);

    /**
     * 引数のレビューを更新する
     *
     * @param model     レビューモデル
     * @param createdId 更新者ID
     * @return レビューモデル
     */
    ReviewModel store(ReviewModel model, String createdId);
}
