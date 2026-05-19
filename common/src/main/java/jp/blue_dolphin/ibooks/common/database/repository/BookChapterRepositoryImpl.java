package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.BookChapterDao;
import jp.blue_dolphin.ibooks.common.database.entity.BookChapter;
import jp.blue_dolphin.ibooks.common.model.BookChapterModel;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ブックチャプターリポジトリ
 */
@AllArgsConstructor
@Repository
public class BookChapterRepositoryImpl implements BookChapterRepository {
    /** ブックチャプターDAO */
    private BookChapterDao bookChapterDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookChapterModel> selectById(Long bookChapterId) {
        if (Objects.isNull(bookChapterId)) {
            return Optional.empty();
        }
        BookChapter entity = bookChapterDao.selectById(bookChapterId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookChapterModel> selectByBookId(Long bookId) {
        if (Objects.isNull(bookId)) {
            return List.of();
        }
        List<BookChapter> entities = bookChapterDao.selectByBookId(bookId);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return List.of();
        }
        List<BookChapterModel> models = new ArrayList<>();
        for (BookChapter entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookChapterModel store(BookChapterModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<BookChapter> result;
        if (Objects.isNull(model.getBookChapterId())) {
            BookChapterModel setModel = model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1).build();
            result = bookChapterDao.insert(convertEntity(setModel));
        } else {
            BookChapterModel setModel = model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = bookChapterDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * 引数のブックチャプターエンティティをブックチャプターモデルに変換する
     *
     * @param entity ブックチャプターエンティティ
     * @return ブックチャプターモデル
     */
    private static BookChapterModel convertModel(BookChapter entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return BookChapterModel.builder()
                .bookChapterId(entity.bookChapterId)
                .bookId(entity.bookId)
                .chapter(entity.chapter)
                .sortOrder(entity.sortOrder)
                .title(entity.title)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のブックチャプターモデルをブックチャプターエンティティに変換する
     *
     * @param model ブックチャプターモデル
     * @return ブックチャプターエンティティ
     */
    private static BookChapter convertEntity(BookChapterModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return BookChapter.builder()
                .bookChapterId(model.getBookChapterId())
                .bookId(model.getBookId())
                .chapter(model.getChapter())
                .sortOrder(model.getSortOrder())
                .title(model.getTitle())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
