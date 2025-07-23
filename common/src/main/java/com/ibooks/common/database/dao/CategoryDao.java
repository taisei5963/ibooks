package com.ibooks.common.database.dao;

import com.ibooks.common.database.entity.Category;
import com.ibooks.common.dto.IdAndName;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;

/**
 * カテゴリDAO
 */
@ConfigAutowireable
@Dao
public interface CategoryDao {
    /**
     * カテゴリを全件取得する
     *
     * @return カテゴリエンティティリスト
     */
    @Select
    List<Category> selectAll();

    /**
     * カテゴリIDを条件に取得する
     *
     * @param categoryId カテゴリID
     * @return カテゴリエンティティ
     */
    @Select
    Category selectById(Long categoryId);

    /**
     * カテゴリコードを条件に取得する
     *
     * @param categoryCode カテゴリコード
     * @return カテゴリエンティティ
     */
    @Select
    Category selectByCode(String categoryCode);

    /**
     * すべてのカテゴリIDとカテゴリ名を取得する
     *
     * @return IDと名称のリスト
     */
    @Select
    List<IdAndName> selectIdAndNames();

    /**
     * 引数のブックIDリストのカテゴリを取得する
     *
     * @param bookIds ブックIDリスト
     * @return カテゴリエンティティリスト
     */
    @Select
    List<Category> selectByBookIds(List<Long> bookIds);

    /**
     * カテゴリを登録する
     *
     * @param entity カテゴリエンティティ
     * @return 登録結果
     */
    @Insert
    Result<Category> insert(Category entity);

    /**
     * カテゴリ更新する
     *
     * @param entity カテゴリエンティティ
     * @return 更新結果
     */
    @Update
    Result<Category> update(Category entity);
}
