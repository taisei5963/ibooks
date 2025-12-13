package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.CategoryBookDao;
import jp.blue_dolphin.ibooks.common.database.entity.SearchCategoryBook;
import jp.blue_dolphin.ibooks.common.model.SearchCategoryBookModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Repository
public class CategoryBookRepositoryImpl implements CategoryBookRepository {
    /** カテゴリ×ブックDAO */
    private CategoryBookDao categoryBookDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchCategoryBookModel> selectByBookId(Long bookId) {
        if (Objects.isNull(bookId)) {
            return List.of();
        }
        List<SearchCategoryBook> entities = categoryBookDao.selectByBookId(bookId);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return List.of();
        }
        List<SearchCategoryBookModel> models = new ArrayList<>();
        for (SearchCategoryBook entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchCategoryBookModel> selectByBookIds(List<Long> bookIds) {
        if (Objects.isNull(bookIds) || bookIds.isEmpty()) {
            return List.of();
        }
        List<SearchCategoryBook> entities = categoryBookDao.selectByBookIds(bookIds);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return List.of();
        }
        List<SearchCategoryBookModel> models = new ArrayList<>();
        for (SearchCategoryBook entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * 引数のカテゴリ×ブック（検索用）エンティティをカテゴリ×ブック（検索用）モデルに変換する
     *
     * @param entity カテゴリ×ブック（検索用）エンティティ
     * @return カテゴリ×ブック（検索用）モデル
     */
    private SearchCategoryBookModel convertModel(SearchCategoryBook entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return SearchCategoryBookModel.builder()
                .bookId(entity.bookId)
                .categoryId1(entity.categoryId1)
                .categoryId2(entity.categoryId2)
                .categoryId3(entity.categoryId3)
                .categoryCode(entity.categoryCode)
                .categoryName(entity.categoryName)
                .build();
    }

    /**
     * 引数のカテゴリ×ブック（検索用）モデルをカテゴリ×ブック（検索用）エンティティに変換する
     *
     * @param model カテゴリ×ブック（検索用）モデル
     * @return カテゴリ×ブック（検索用）エンティティ
     */
    private SearchCategoryBook convertEntity(SearchCategoryBookModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return SearchCategoryBook.builder()
                .bookId(model.getBookId())
                .categoryId1(model.getCategoryId1())
                .categoryId2(model.getCategoryId2())
                .categoryId3(model.getCategoryId3())
                .categoryCode(model.getCategoryCode())
                .categoryName(model.getCategoryName())
                .build();
    }
}
