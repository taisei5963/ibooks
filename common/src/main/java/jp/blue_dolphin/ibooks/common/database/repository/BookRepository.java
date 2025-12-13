package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.csv.BookCsv;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ブックリポジトリ
 */
@Repository
public interface BookRepository {
    /**
     * 引数のブックIDを条件に取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    Optional<BookModel> selectById(Long bookId);

    /**
     * 引数のJANコードを条件に取得する
     *
     * @param janCode JANコード
     * @return ブックモデル
     */
    Optional<BookModel> selectByJanCode(String janCode);

    /**
     * 引数のJANコードリストを条件に使用する
     *
     * @param janCodes JANコードリスト
     * @return ブックモデルリスト
     */
    List<BookModel> selectByJanCodes(List<String> janCodes);

    /**
     * 引数の検索条件にブック情報を取得する
     *
     * @param title      タイトル
     * @param author     著者
     * @param publisher  出版社
     * @param categoryId カテゴリID
     * @param orderBy    ソート順
     * @param options    検索オプション
     * @return 検索結果
     */
    SearchResult<BookModel> selectBySearchCond(String title, String author, String publisher,
                                               Long categoryId, String orderBy,
                                               SelectOptions options);

    /**
     * 引数の条件でブックCSVを取得する
     *
     * @param title      タイトル
     * @param author     著者
     * @param publisher  出版社
     * @param categoryId カテゴリID
     * @param orderBy    ソート順
     * @param options    検索用オプション
     * @return ブックCSVリスト
     */
    SearchResult<BookCsv> selectBySearchCondForCsv(String title, String author, String publisher,
                                                   Long categoryId, String orderBy,
                                                   SelectOptions options);

    /**
     * 引数のタイトルと出版社で取得する
     *
     * @param title     タイトル
     * @param publisher 出版社
     * @return ブックモデル
     */
    Optional<BookModel> selectByTitleAndPublisher(String title, String publisher);

    /**
     * 引数のブック情報を更新する
     *
     * @param model     ブックモデル
     * @param createdId 更新者ID
     * @return ブックモデル
     */
    BookModel store(BookModel model, String createdId);
}
