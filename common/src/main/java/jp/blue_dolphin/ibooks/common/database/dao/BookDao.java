package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.Book;
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
     * 引数のブックIDを条件に取得する
     *
     * @param bookId ブック
     * @return ブックエンティティ
     */
    @Select
    Book selectById(Long bookId);

    /**
     * 引数のJANコードを条件に取得する
     *
     * @param janCode JANコード
     * @return ブックエンティティ
     */
    @Select
    Book selectByJanCode(String janCode);

    /**
     * JANコードリストを条件に取得する
     *
     * @param janCodes JANコードリスト
     * @return ブックエンティティ
     */
    @Select
    List<Book> selectByJanCodes(List<String> janCodes);

    /**
     * 引数の検索条件で取得する
     *
     * @param title      タイトル
     * @param author     著者
     * @param publisher  出版社
     * @param categoryId カテゴリID
     * @param orderBy    ソート順
     * @param options    検索オプション
     * @return ブックエンティティ
     */
    @Select
    List<Book> selectBySearchCond(String title, String author, String publisher, Long categoryId,
                                  String orderBy, SelectOptions options);

    /**
     * 引数のタイトルと出版社で取得する
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
     * ブックを削除する
     *
     * @param entity ブックエンティティ
     * @return 削除結果
     */
    @Delete
    Result<Book> delete(Book entity);
}
