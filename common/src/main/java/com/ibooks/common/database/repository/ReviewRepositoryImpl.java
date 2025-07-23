package com.ibooks.common.database.repository;

import com.ibooks.common.database.dao.ReviewDao;
import com.ibooks.common.database.entity.Review;
import com.ibooks.common.model.ReviewModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * レビューレポジトリ
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
    public Optional<ReviewModel> selectById(Long reviewId, Long bookId) {
        if (Objects.isNull(reviewId)) {
            return Optional.empty();
        }
        Review entity = reviewDao.selectById(reviewId, bookId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer countByBookId(Long bookId, Integer kind) {
        if (Objects.isNull(bookId)) {
            return null;
        }
        if (Objects.isNull(kind) || kind == 0) {
            return reviewDao.countByBookId(bookId, 0);
        }
        return reviewDao.countByBookId(bookId, kind);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewModel store(ReviewModel model, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Review> result;
        if (Objects.isNull(model.getReviewId())) {
            ReviewModel setModel = model.toBuilder().createDate(now).updateDate(now).createId(registerId).ver(1)
                    .build();
            result = reviewDao.insert(convertEntity(setModel));
        } else {
            ReviewModel setModel = model.toBuilder().updateDate(now).createId(registerId).build();
            result = reviewDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(ReviewModel model) {
        if (Objects.isNull(model)) {
            return 0;
        }
        Result<Review> result = reviewDao.delete(convertEntity(model));
        return result.getCount();
    }

    /**
     * レビューエンティティをレビューモデルに変換する
     *
     * @param entity レビューエンティティ
     * @return レビューモデル
     */
    private ReviewModel convertModel(Review entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return ReviewModel.builder()
                .reviewerId(entity.reviewId)
                .bookId(entity.bookId)
                .reviewerId(entity.reviewerId)
                .rating(entity.rating)
                .comment(entity.comment)
                .reviewStatus(entity.reviewStatus)
                .createDate(entity.createdAt)
                .updateDate(entity.updatedAt)
                .createId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * レビューモデルをレビューエンティティに変換する
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
                .bookId(model.getBookId())
                .reviewerId(model.getReviewerId())
                .rating(model.getRating())
                .comment(model.getComment())
                .reviewStatus(model.getReviewStatus())
                .createdAt(model.getCreateDate())
                .updatedAt(model.getUpdateDate())
                .createdId(model.getCreateId())
                .ver(model.getVer())
                .build();
    }
}
