package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.BookChapter;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;

/**
 * ブックチャプターDAO
 */
@ConfigAutowireable
@Dao
public interface BookChapterDao {

    /**
     * 引数のIDを条件に取得する
     *
     * @param bookChapterId ブックチャプターID
     * @return ブックチャプターエンティティ
     */
    @Select
    BookChapter selectById(Long bookChapterId);

    /**
     * 引数のブックIDを条件に取得する
     *
     * @param bookId ブックID
     * @return ブックチャプターエンティティリスト
     */
    @Select
    List<BookChapter> selectByBookId(Long bookId);

    /**
     * ブックチャプターを登録する
     *
     * @param entity ブックチャプターエンティティ
     * @return 登録結果
     */
    @Insert
    Result<BookChapter> insert(BookChapter entity);

    /**
     * ブックチャプターを更新する
     *
     * @param entity ブックチャプターエンティティ
     * @return 更新結果
     */
    @Update
    Result<BookChapter> update(BookChapter entity);

    /**
     * ブックチャプターを削除する
     *
     * @param entity ブックチャプターエンティティ
     * @return 削除結果
     */
    @Delete
    Result<BookChapter> delete(BookChapter entity);
}
