package com.techcof.ITive.common.database.repository;

import com.techcof.ITive.common.model.SearchCategoryBookModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * カテゴリ×ブックリポジトリ
 */
@Repository
public interface CategoryBookRepository {
    /**
     * 引数のブックIDのカテゴリ×ブックを取得する
     *
     * @param bookId ブックID
     * @return カテゴリ×ブック（検索用）モデルリスト
     */
    List<SearchCategoryBookModel> selectByBookId(Long bookId);

    /**
     * 引数のブックIDリストのカテゴリ×ブックを取得する
     *
     * @param bookIds ブックIDリスト
     * @return カテゴリ×ブック（検索用）モデルリスト
     */
    List<SearchCategoryBookModel> selectByBookIds(List<Long> bookIds);
}
