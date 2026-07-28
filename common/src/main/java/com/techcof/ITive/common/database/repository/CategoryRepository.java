package com.techcof.ITive.common.database.repository;

import com.techcof.ITive.common.dto.IdAndName;
import com.techcof.ITive.common.model.CategoryModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * カテゴリリポジトリ
 */
@Repository
public interface CategoryRepository {
    /**
     * 引数のカテゴリIDを条件に取得する
     *
     * @param categoryId カテゴリID
     * @return カテゴリモデル
     */
    Optional<CategoryModel> selectById(Long categoryId);

    /**
     * 引数のカテゴリコードを条件に取得する
     *
     * @param categoryCode カテゴリコード
     * @return カテゴリモデル
     */
    Optional<CategoryModel> selectByCode(String categoryCode);

    /**
     * カテゴリIDとカテゴリ名のリストを取得する
     *
     * @return カテゴリIDとカテゴリ名のリスト
     */
    List<IdAndName> selectIdAndNames();

    /**
     * カテゴリを全件取得する
     *
     * @return カテゴリモデルリスト
     */
    List<CategoryModel> selectAll();

    /**
     * 引数のカテゴリモデルで更新する
     *
     * @param model     カテゴリモデル
     * @param createdId 更新者ID
     * @return カテゴリモデル
     */
    CategoryModel store(CategoryModel model, String createdId);

    /**
     * 引数のカテゴリを削除する
     *
     * @param model カテゴリモデル
     * @return 削除件数
     */
    int delete(CategoryModel model);
}
