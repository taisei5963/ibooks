package com.ibooks.common.database.repository;

import com.ibooks.common.dto.IdAndName;
import com.ibooks.common.model.CategoryModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * カテゴリレポジトリ
 */
@Repository
public interface CategoryRepository {
    /**
     * カテゴリを全件取得する
     *
     * @return カテゴリモデルリスト
     */
    List<CategoryModel> selectAll();

    /**
     * 引数のカテゴリIDのカテゴリを取得する
     *
     * @param categoryId カテゴリID
     * @return カテゴリモデル
     */
    Optional<CategoryModel> selectById(Long categoryId);

    /**
     * 引数のカテゴリコードのカテゴリを取得する
     *
     * @param categoryCode カテゴリコード
     * @return カテゴリモデル
     */
    Optional<CategoryModel> selectByCode(String categoryCode);

    /**
     * すべてのカテゴリIDとカテゴリ名を取得する
     *
     * @return IDと名称のリスト
     */
    List<IdAndName> selectIdAndNames();

    /**
     * 引数のブックIDのリストのカテゴリを取得する
     *
     * @param bookIds ブックIDリスト
     * @return カテゴリモデル
     */
    List<CategoryModel> selectByBookIds(List<Long> bookIds);

    /**
     * 引数のカテゴリを更新する
     *
     * @param category   カテゴリモデル
     * @param registerId 登録者ID
     * @return カテゴリモデル
     */
    CategoryModel store(CategoryModel category, String registerId);
}
