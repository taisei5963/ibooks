package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.ReviewerDao;
import jp.blue_dolphin.ibooks.common.database.entity.Reviewer;
import jp.blue_dolphin.ibooks.common.model.ReviewerModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * レビュワーリポジトリ
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
    public ReviewerModel selectById(Long reviewerId) {
        if (Objects.isNull(reviewerId)) {
            return null;
        }
        Reviewer reviewer = reviewerDao.selectById(reviewerId);
        return convertModel(reviewer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ReviewerModel> selectByLoginId(String loginId) {
        if (Objects.isNull(loginId)) {
            return Optional.empty();
        }
        List<Reviewer> entities = reviewerDao.selectByLoginId(loginId);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertModel(entities.getFirst()));
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
                .accountStatus(entity.accountStatus)
                .lastLoginDate(entity.lastLoginDate)
                .loginFailureCount(entity.loginFailureCount)
                .loginFailureDate(entity.loginFailureDate)
                .passwordRemindDate(entity.passwordRemindDate)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
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
                .accountStatus(model.getAccountStatus())
                .lastLoginDate(model.getLastLoginDate())
                .loginFailureCount(model.getLoginFailureCount())
                .loginFailureDate(model.getLoginFailureDate())
                .passwordRemindDate(model.getPasswordRemindDate())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
