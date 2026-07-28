package com.techcof.ITive.common.database.repository;

import com.techcof.ITive.common.model.BookGuideModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ブックガイドリポジトリ
 */
@Repository
public interface BookGuideRepository {

    /**
     * 引数のブックガイドIDを条件に取得する
     *
     * @param bookGuideId ブックガイドID
     * @return ブックガイドモデル
     */
    Optional<BookGuideModel> selectById(Long bookGuideId);

    /**
     * 引数のブックIDを条件に取得する
     *
     * @param bookId ブックID
     * @return ブックガイドモデル
     */
    Optional<BookGuideModel> selectByBookId(Long bookId);

    /**
     * 引数のブックガイドモデルで更新する
     *
     * @param model     ブックガイドモデル
     * @param createdId 更新者ID
     * @return ブックガイドモデル
     */
    BookGuideModel store(BookGuideModel model, String createdId);
}
