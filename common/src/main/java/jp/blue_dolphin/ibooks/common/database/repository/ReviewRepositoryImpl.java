package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.ReviewDao;
import jp.blue_dolphin.ibooks.common.database.entity.Review;
import jp.blue_dolphin.ibooks.common.model.ReviewModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * レビューリポジトリ
 */
@AllArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    /** レビューDAO */
    private ReviewDao reviewDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewModel selectByIdAndBookId(Long reviewId, Long bookId) {
        if (Objects.isNull(reviewId) || Objects.isNull(bookId)) {
            return null;
        }
        Review entity = reviewDao.selectByIdAndBookId(reviewId, bookId);
        return convertModel(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countRatingByBookId(Long bookId, int kind) {
        return reviewDao.countRatingByBookId(bookId, kind);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal avgRatingByBookId(Long bookId) {
        if (Objects.isNull(bookId)) {
            return null;
        }
        return reviewDao.avgRatingByBookId(bookId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewModel store(ReviewModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Review> result;
        if (Objects.isNull(model.getReviewId())) {
            ReviewModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = reviewDao.insert(convertEntity(setModel));
        } else {
            ReviewModel setModel = model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = reviewDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * 引数のレビューエンティティをレビューモデルに変換する
     *
     * @param entity レビューエンティティ
     * @return レビューモデル
     */
    private ReviewModel convertModel(Review entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return ReviewModel.builder()
                .reviewId(entity.reviewId)
                .reviewerId(entity.reviewerId)
                .bookId(entity.bookId)
                .rating(entity.rating)
                .comment(entity.comment)
                .reviewViewFlag(entity.reviewViewFlag)
                .reviewDate(entity.reviewDate)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のレビューモデルをレビューエンティティに変換する
     *
     * @param model レビューモデル
     * @return レビューエンティティ
     */
    private Review convertEntity(ReviewModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Review.builder()
                .reviewId(model.getReviewId())
                .reviewerId(model.getReviewerId())
                .bookId(model.getBookId())
                .rating(model.getRating())
                .comment(model.getComment())
                .reviewViewFlag(model.getReviewViewFlag())
                .reviewDate(model.getReviewDate())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
