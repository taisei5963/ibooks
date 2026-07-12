package jp.techcof.ITive.common.database.dao;

import jp.techcof.ITive.common.database.entity.SearchCategoryBook;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * カテゴリ×ブックDAO
 */
@ConfigAutowireable
@Dao
public interface CategoryBookDao {
    /**
     * 引数のブックIDのカテゴリ×ブックを取得する
     *
     * @param bookId ブックID
     * @return カテゴリ×ブック（検索用）エンティティリスト
     */
    @Select
    List<SearchCategoryBook> selectByBookId(Long bookId);

    /**
     * 引数のブックIDリストのカテゴリ×ブックを取得する
     *
     * @param bookIds ブックIDリスト
     * @return カテゴリ×ブック（検索用）エンティティリスト
     */
    @Select
    List<SearchCategoryBook> selectByBookIds(List<Long> bookIds);
}
