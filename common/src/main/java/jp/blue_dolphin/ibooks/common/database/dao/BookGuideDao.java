package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.database.entity.BookGuide;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

/**
 * ブックガイドDAO
 */
@ConfigAutowireable
@Dao
public interface BookGuideDao {
    /**
     * 引数のブックガイドIDを条件に取得する
     *
     * @param bookGuideId ブックガイドID
     * @return ブックガイドエンティティ
     */
    @Select
    BookGuide selectById(Long bookGuideId);

    /**
     * 引数のブックIDを条件に取得する
     *
     * @param bookId ブックID
     * @return ブックガイドエンティティ
     */
    @Select
    BookGuide selectByBookId(Long bookId);

    /**
     * ブックガイドを登録する
     *
     * @param entity ブックガイドエンティティ
     * @return 登録結果
     */
    @Insert
    Result<BookGuide> insert(BookGuide entity);

    /**
     * ブックガイドを更新する
     *
     * @param entity ブックガイドエンティティ
     * @return 更新結果
     */
    @Update
    Result<BookGuide> update(BookGuide entity);

    /**
     * ブックガイドを削除する
     *
     * @param entity ブックガイドエンティティ
     * @return 削除結果
     */
    @Delete
    Result<BookGuide> delete(BookGuide entity);
}
