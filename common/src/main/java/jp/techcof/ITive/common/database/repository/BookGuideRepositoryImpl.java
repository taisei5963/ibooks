package jp.techcof.ITive.common.database.repository;

import jp.techcof.ITive.common.database.dao.BookGuideDao;
import jp.techcof.ITive.common.database.entity.BookGuide;
import jp.techcof.ITive.common.model.BookGuideModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * ブックガイドリポジトリ
 */
@AllArgsConstructor
@Repository
public class BookGuideRepositoryImpl implements BookGuideRepository {
    /** ブックガイドDAO */
    private BookGuideDao bookGuideDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookGuideModel> selectById(Long bookGuideId) {
        if (Objects.isNull(bookGuideId)) {
            return Optional.empty();
        }
        BookGuide entity = bookGuideDao.selectById(bookGuideId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookGuideModel> selectByBookId(Long bookId) {
        if (Objects.isNull(bookId)) {
            return Optional.empty();
        }
        BookGuide entity = bookGuideDao.selectByBookId(bookId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookGuideModel store(BookGuideModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<BookGuide> result;
        if (Objects.isNull(model.getBookGuideId())) {
            BookGuideModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = bookGuideDao.insert(convertEntity(setModel));
        } else {
            BookGuideModel setModel = model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = bookGuideDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * 引数のブックガイドエンティティをブックガイドモデルに変換する
     *
     * @param entity ブックガイドエンティティ
     * @return ブックガイドモデル
     */
    private static BookGuideModel convertModel(BookGuide entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return BookGuideModel.builder()
                .bookGuideId(entity.bookGuideId)
                .bookId(entity.bookId)
                .recommendFor(entity.recommendFor)
                .recommendedTiming(entity.recommendedTiming)
                .recommendedPoints(entity.recommendedPoints)
                .nextRecommended(entity.nextRecommended)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のブックガイドモデルをブックガイドエンティティに変換する
     *
     * @param model ブックガイドモデル
     * @return ブックガイドエンティティ
     */
    private static BookGuide convertEntity(BookGuideModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return BookGuide.builder()
                .bookGuideId(model.getBookGuideId())
                .bookId(model.getBookId())
                .recommendFor(model.getRecommendFor())
                .recommendedTiming(model.getRecommendedTiming())
                .recommendedPoints(model.getRecommendedPoints())
                .nextRecommended(model.getNextRecommended())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
