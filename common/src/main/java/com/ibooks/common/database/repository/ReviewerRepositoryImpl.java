package com.ibooks.common.database.repository;

import com.ibooks.common.database.dao.ReviewerDao;
import com.ibooks.common.database.entity.Reviewer;
import com.ibooks.common.dto.IdAndCodeAndName;
import com.ibooks.common.model.ReviewerModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * レビュワーレポジトリ
 */
@AllArgsConstructor
@Repository
public class ReviewerRepositoryImpl implements ReviewerRepository {
    /** レビュワーDAO */
    private ReviewerDao reviewerDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ReviewerModel> selectById(Long reviewerId) {
        Reviewer entity = reviewerDao.selectById(reviewerId);
        if (Objects.isNull(entity)) {
            return Optional.empty();
        }
        return Optional.of(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    public Optional<ReviewerModel> selectByLoginId(String loginId) {
        List<Reviewer> reviewers = reviewerDao.selectByLoginId(loginId);
        if (Objects.isNull(reviewers) || reviewers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(convertModel(reviewers.getFirst()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IdAndCodeAndName> selectIdAndCodeAndName() {
        return reviewerDao.selectIdAndCodeAndName();
    }

    /**
     * {@inheritDoc}
     */
    public ReviewerModel store(ReviewerModel model, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Reviewer> result;
        if (Objects.isNull(model.getReviewerId())) {
            ReviewerModel setModel = model.toBuilder().createDate(now).updateDate(now).loginFailureCount(0)
                    .createId(registerId).ver(1).build();
            result = reviewerDao.insert(convertEntity(setModel));
        } else {
            ReviewerModel setModel = model.toBuilder().updateDate(now).createId(registerId).build();
            result = reviewerDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteById(Long reviewerId, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        return reviewerDao.deleteById(reviewerId, now, registerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewerModel updateLastLoginDate(ReviewerModel model) {
        LocalDateTime now = LocalDateTime.now();
        ReviewerModel setModel = model.toBuilder().loginFailureCount(0).loginFailureDate(now).updateDate(now).build();
        Result<Reviewer> result = reviewerDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewerModel inCreaseLoginFailureCount(ReviewerModel model) {
        LocalDateTime now = LocalDateTime.now();
        ReviewerModel setModel = model.toBuilder().loginFailureCount(model.getLoginFailureCount() + 1)
                .loginFailureDate(now).updateDate(now).build();
        Result<Reviewer> result = reviewerDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewerModel resetLoginFailureCount(ReviewerModel model) {
        LocalDateTime now = LocalDateTime.now();
        ReviewerModel setModel = model.toBuilder().loginFailureCount(0).updateDate(now).build();
        Result<Reviewer> result = reviewerDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * レビュワーエンティティをレビュワーモデルに変換する
     *
     * @param entity レビュワーエンティティ
     * @return レビュワーモデル
     */
    private ReviewerModel convertModel(Reviewer entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return ReviewerModel.builder()
                .reviewerId(entity.reviewerId)
                .loginId(entity.loginId)
                .password(entity.password)
                .name(entity.name)
                .lastLoginDate(entity.lastLoginDate)
                .accountStatus(entity.accountStatus)
                .loginFailureCount(entity.loginFailureCount)
                .loginFailureDate(entity.loginFailureDate)
                .createDate(entity.createdAt)
                .updateDate(entity.updatedAt)
                .createId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * レビュワーモデルをレビュワーエンティティに変換する
     *
     * @param model レビュワーモデル
     * @return レビュワーエンティティ
     */
    private Reviewer convertEntity(ReviewerModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Reviewer.builder()
                .reviewerId(model.getReviewerId())
                .loginId(model.getLoginId())
                .password(model.getPassword())
                .name(model.getName())
                .lastLoginDate(model.getLastLoginDate())
                .accountStatus(model.getAccountStatus())
                .loginFailureCount(model.getLoginFailureCount())
                .loginFailureDate(model.getLoginFailureDate())
                .createdAt(model.getCreateDate())
                .updatedAt(model.getUpdateDate())
                .createdId(model.getCreateId())
                .ver(model.getVer())
                .build();
    }
}
