package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.CategoryDao;
import jp.blue_dolphin.ibooks.common.database.entity.Category;
import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import jp.blue_dolphin.ibooks.common.model.CategoryModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * カテゴリリポジトリ
 */
@AllArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    /** カテゴリDAO */
    private CategoryDao categoryDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IdAndName> selectIdAndNames() {
        return categoryDao.selectIdAndNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryModel> selectAll() {
        List<Category> entities = categoryDao.selectAll();
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return List.of();
        }
        List<CategoryModel> models = new ArrayList<>();
        for (Category entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryModel store(CategoryModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(model)) {
            return null;
        }
        Result<Category> result;
        if (model.getCategoryId() == null) {
            CategoryModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = categoryDao.insert(convertEntity(setModel));
        } else {
            CategoryModel setModel = model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = categoryDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(CategoryModel model) {
        if (Objects.isNull(model)) {
            return 0;
        }
        Result<Category> result = categoryDao.delete(convertEntity(model));
        return result.getCount();
    }

    /**
     * 引数のカテゴリエンティティをカテゴリモデルに変換する
     *
     * @param entity カテゴリエンティティ
     * @return カテゴリモデル
     */
    private CategoryModel convertModel(Category entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return CategoryModel.builder()
                .categoryId(entity.categoryId)
                .categoryCode(entity.categoryCode)
                .categoryName(entity.categoryName)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のカテゴリモデルをカテゴリエンティティに変換する
     *
     * @param model カテゴリモデル
     * @return カテゴリエンティティ
     */
    private Category convertEntity(CategoryModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Category.builder()
                .categoryId(model.getCategoryId())
                .categoryCode(model.getCategoryCode())
                .categoryName(model.getCategoryName())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
