package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.model.BookChapterModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ブックチャプターリポジトリ
 */
@Repository
public interface BookChapterRepository {

    /**
     * 引数のブックチャプターIDを条件に取得する
     *
     * @param bookChapterId ブックチャプターID
     * @return ブックチャプターモデル
     */
    Optional<BookChapterModel> selectById(Long bookChapterId);

    /**
     * 引数のブックIDを条件に取得する
     *
     * @param bookId ブックID
     * @return ブックチャプターモデルリスト
     */
    List<BookChapterModel> selectByBookId(Long bookId);

    /**
     * 引数のブックチャプター情報を更新する
     *
     * @param model     ブックチャプターモデル
     * @param createdId 更新者ID
     * @return ブックチャプターモデル
     */
    BookChapterModel store(BookChapterModel model, String createdId);
}
