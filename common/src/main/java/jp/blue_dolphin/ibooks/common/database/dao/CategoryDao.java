package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.Category;
import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
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
     * 引数のカテゴリIDを条件に取得する
     *
     * @param categoryId カテゴリID
     * @return カテゴリエンティティ
     */
    @Select
    Category selectById(Long categoryId);

    /**
     * 引数のカテゴリコードを条件に取得する
     * @param categoryCode カテゴリコード
     * @return カテゴリエンティティ
     */
    @Select
    Category selectByCode(String categoryCode);

    /**
     * カテゴリを全件取得する
     *
     * @return カテゴリエンティティリスト
     */
    @Select
    List<IdAndName> selectIdAndNames();

    /**
     * カテゴリを全件取得する
     *
     * @return カテゴリエンティティリスト
     */
    @Select
    List<Category> selectAll();

    /**
     * 登録
     *
     * @param entity カテゴリエンティティ
     * @return 登録結果
     */
    @Insert
    Result<Category> insert(Category entity);

    /**
     * 更新
     *
     * @param entity カテゴリエンティティ
     * @return 更新結果
     */
    @Update
    Result<Category> update(Category entity);

    /**
     * 削除
     *
     * @param entity カテゴリエンティティ
     * @return 削除結果
     */
    @Delete
    Result<Category> delete(Category entity);
}
