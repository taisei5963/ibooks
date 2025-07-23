package com.ibooks.common.database.repository;

import com.ibooks.common.database.dao.CategoryDao;
import com.ibooks.common.database.entity.Category;
import com.ibooks.common.dto.IdAndName;
import com.ibooks.common.model.CategoryModel;
import com.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * カテゴリレポジトリ
 */
@AllArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    /**
     * カテゴリDAO
     */
    private CategoryDao categoryDao;

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
    public Optional<CategoryModel> selectById(Long categoryId) {
        if (Objects.isNull(categoryId)) {
            return Optional.empty();
        }
        Category entity = categoryDao.selectById(categoryId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CategoryModel> selectByCode(String categoryCode) {
        if (Strings.isEmpty(categoryCode)) {
            return Optional.empty();
        }
        Category entity = categoryDao.selectByCode(categoryCode);
        return Optional.ofNullable(convertModel(entity));
    }

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
    public List<CategoryModel> selectByBookIds(List<Long> bookIds) {
        if (!Objects.isNull(bookIds) || bookIds.isEmpty()) {
            return List.of();
        }
        List<Category> entities = categoryDao.selectByBookIds(bookIds);
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
    public CategoryModel store(CategoryModel category, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Category> result;
        if (Objects.isNull(category.getCategoryId())) {
            CategoryModel setModel = category.toBuilder().createDate(now).updateDate(now).createId(registerId).ver(1)
                    .build();
            result = categoryDao.insert(convertEntity(setModel));
        } else {
            CategoryModel setModel = category.toBuilder().updateDate(now).createId(registerId).build();
            result = categoryDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * カテゴリエンティティをカテゴリモデルに変換する
     *
     * @param entity カテゴリエンティティ
     * @return カテゴリモデル
     */
    private static CategoryModel convertModel(Category entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return CategoryModel.builder()
                .categoryId(entity.categoryId)
                .categoryCode(entity.categoryCode)
                .categoryName(entity.categoryName)
                .createDate(entity.createdAt)
                .updateDate(entity.updatedAt)
                .createId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * カテゴリモデルをカテゴリエンティティに変換する
     *
     * @param model カテゴリモデル
     * @return カテゴリエンティティ
     */
    private static Category convertEntity(CategoryModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Category.builder()
                .categoryId(model.getCategoryId())
                .categoryCode(model.getCategoryCode())
                .categoryName(model.getCategoryName())
                .createdAt(model.getCreateDate())
                .updatedAt(model.getUpdateDate())
                .ver(model.getVer())
                .build();
    }
}
