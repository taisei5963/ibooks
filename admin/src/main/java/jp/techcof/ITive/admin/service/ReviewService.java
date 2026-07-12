package jp.techcof.ITive.admin.service;

import jp.techcof.ITive.common.database.repository.ReviewRepository;
import jp.techcof.ITive.common.model.ReviewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * レビューサービス
 */
@AllArgsConstructor
@Service
public class ReviewService {
    /** レビューレポジトリ */
    private ReviewRepository reviewRepository;

    /**
     * 引数のレビューIDとブック情報に紐づくレビュー情報を取得する
     *
     * @param reviewId レビューID
     * @param bookId   ブックID
     * @return レビューモデル
     */
    public List<ReviewModel> selectByIdAndBookId(Long reviewId, Long bookId) {
        return reviewRepository.selectByIdAndBookId(reviewId, bookId);
    }
}
