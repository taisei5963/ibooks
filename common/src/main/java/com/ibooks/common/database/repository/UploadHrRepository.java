package com.ibooks.common.database.repository;

import com.ibooks.common.constants.UploadType;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.UploadHrModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * アップロード履歴レポジトリ
 */
@Repository
public interface UploadHrRepository {
    /**
     * 引数のアップロード履歴IDのアップロード履歴モデルを取得する
     *
     * @param uploadHrId アップロード履歴ID
     * @return アップロード履歴モデル
     */
    Optional<UploadHrModel> restore(Long uploadHrId);

    /**
     * 引数のアップロード履歴モデルを登録する
     *
     * @param uploadHrModel アップロード履歴モデル
     * @param registerId    登録者ID
     * @return アップロード履歴モデル
     */
    UploadHrModel store(UploadHrModel uploadHrModel, String registerId);

    /**
     * アップロード履歴を物理削除
     *
     * @param uploadHrIdList 削除対象のアップロード履歴IDリスト
     */
    void deletePhysical(List<Long> uploadHrIdList);

    /**
     * 引数のアップロード種別の最新レコードを取得する
     *
     * @param uploadType アップロード種別
     * @return アップロード履歴モデル
     */
    Optional<UploadHrModel> selectLatestByUploadType(UploadType uploadType);

    /**
     * 引数の検索条件でアップロード履歴を取得する
     *
     * @param uploadType   アップロード種別
     * @param uploadStatus アップロードステータス
     * @param siteType     サイト種別
     * @param registerId   登録者ID
     * @param orderBy      ソート順
     * @param options      検索オプション
     * @return 検索結果
     */
    SearchResult<UploadHrModel> selectByCond(String uploadType, String uploadStatus, String siteType, String registerId,
                                             String orderBy, SelectOptions options);
}
