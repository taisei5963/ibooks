package com.techcof.ITive.common.database.repository;

import com.techcof.ITive.common.model.ReviewerModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * レビュワーリポジトリ
 */
@Repository
public interface ReviewerRepository {
    /**
     * 引数のレビュワーIDを条件に取得する
     *
     * @param reviewerId レビュワーID
     * @return レビュワーエンティティ
     */
    public ReviewerModel selectById(Long reviewerId);

    /**
     * 引数のログインIDを除権に取得する
     * @param loginId ログインID
     * @return レビュワーモデル
     */
    public Optional<ReviewerModel> selectByLoginId(String loginId);
}
