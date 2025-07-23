package com.ibooks.common.database.repository;

import com.ibooks.common.csv.BookCsv;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.BookModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ブックレポジトリ
 */
@Repository
public interface BookRepository {
    /**
     * 引数のブックIDのブック情報を取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    Optional<BookModel> selectById(Long bookId);

    /**
     * 引数のブックコードのブック情報を取得する
     *
     * @param bookCode ブックコード
     * @return ブックモデル
     */
    Optional<BookModel> selectByCode(String bookCode);

    /**
     * 引数の条件でブック情報を取得する
     *
     * @param bookCode   ブックコード
     * @param title      タイトル
     * @param author     著者
     * @param publisher  出版社
     * @param categoryId カテゴリID
     * @param userId     ユーザーID
     * @param orderBy    ソート順
     * @param options    検索オプション
     * @return ブックCSVリスト
     */
    SearchResult<BookCsv> selectBySearchCond(String bookCode, String title, String author, String publisher,
                                             Long categoryId, Long userId, String orderBy, SelectOptions options);

    /**
     * 引数のタイトルと出版社のブック情報を取得する
     *
     * @param title     タイトル
     * @param publisher 出版社
     * @return ブックモデル
     */
    Optional<BookModel> selectByTitleAndPublisher(String title, String publisher);

    /**
     * 引数のブック情報を更新する
     *
     * @param model      ブックモデル
     * @param registerId 登録者ID
     * @return ブックモデル
     */
    BookModel store(BookModel model, String registerId);

    /**
     * 引数のIDの評価値の平均値を再計算して更新する
     *
     * @param bookId ブックID
     * @return 更新件数
     */
    int avgRatingByBookId(Long bookId);

    /**
     * 引数のブック情報を削除する
     *
     * @return 削除件数
     */
    int delete(BookModel model);
}
