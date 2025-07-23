package com.ibooks.common.database.repository;

import com.ibooks.common.dto.IdAndCodeAndName;
import com.ibooks.common.model.ReviewerModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * レビュワーレポジトリ
 */
@Repository
public interface ReviewerRepository {
    /**
     * 引数のレビュワーIDのレビュワーを取得する
     *
     * @param reviewerId レビュワーID
     * @return レビュワーモデル
     */
    Optional<ReviewerModel> selectById(Long reviewerId);

    /**
     * レビュワーIDとコード、レビュワー名のリストを取得する
     *
     * @return レビュワーIDとコード、レビュワー名のリスト
     */
    List<IdAndCodeAndName> selectIdAndCodeAndName();

    /**
     * 引数のレビュワーを更新する
     *
     * @param model      レビュワーモデル
     * @param registerId 登録者ID
     * @return レビュワーモデル
     */
    ReviewerModel store(ReviewerModel model, String registerId);

    /**
     * 引数のレビュワーを削除する
     *
     * @param reviewerId    削除対象のレビュワーID
     * @param registerId 登録者ID
     * @return 削除結果
     */
    int deleteById(Long reviewerId, String registerId);

    /**
     * 最終ログイン日時を更新する
     *
     * @param model レビュワーモデル
     * @return レビュワーモデル
     */
    ReviewerModel updateLastLoginDate(ReviewerModel model);

    /**
     * ログイン失敗回数を増やす
     *
     * @param model レビュワーモデル
     * @return レビュワーモデル
     */
    ReviewerModel inCreaseLoginFailureCount(ReviewerModel model);

    /**
     * ログイン失敗回数をリセットする
     *
     * @param model レビュワーモデル
     * @return レビュワーモデル
     */
    ReviewerModel resetLoginFailureCount(ReviewerModel model);
}
