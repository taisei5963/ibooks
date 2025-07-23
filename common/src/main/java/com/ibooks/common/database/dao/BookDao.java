package com.ibooks.common.database.dao;

import com.ibooks.common.database.entity.Book;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * ブックDAO
 */
@ConfigAutowireable
@Dao
public interface BookDao {
    /**
     * ブックIDを条件で取得する
     *
     * @param bookId ブックID
     * @return ブックエンティティ
     */
    @Select
    Book selectById(Long bookId);

    /**
     * ブックコードを条件で取得する
     *
     * @param bookCode ブックコード
     * @return ブックエンティティ
     */
    @Select
    Book selectByCode(String bookCode);

    /**
     * 引数の条件で取得する
     *
     * @param bookCode   ブックコード
     * @param title      タイトル
     * @param author     著者
     * @param publisher  出版社
     * @param categoryId カテゴリID
     * @param userId     ユーザーID
     * @return ブック（検索用）エンティティリスト
     */
    @Select
    List<Book> selectBySearchCond(String bookCode, String title, String author, String publisher, Long categoryId,
                                  Long userId, String orderBy, SelectOptions options);

    /**
     * タイトルと出版社を条件にブックを取得する
     *
     * @param title     タイトル
     * @param publisher 出版社
     * @return ブックエンティティ
     */
    @Select
    Book selectByTitleAndPublisher(String title, String publisher);

    /**
     * ブックを登録する
     *
     * @param entity ブックエンティティ
     * @return 登録結果
     */
    @Insert
    Result<Book> insert(Book entity);

    /**
     * ブックを更新する
     *
     * @param entity ブックエンティティ
     * @return 更新結果
     */
    @Update
    Result<Book> update(Book entity);

    /**
     * IDを条件に評価値の平均を再計算する
     *
     * @param bookId ブックID
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int avgRatingById(Long bookId);

    /**
     * ブックを削除する
     *
     * @param entity ブックエンティティ
     * @return 削除結果
     */
    @Delete
    Result<Book> delete(Book entity);
}
