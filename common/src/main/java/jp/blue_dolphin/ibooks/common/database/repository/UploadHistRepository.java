package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.UploadHistModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * アップロード履歴リポジトリ
 */
@Repository
public interface UploadHistRepository {
    /**
     * 引数のアップロード履歴IDのアップロード履歴モデルを取得する
     *
     * @param uploadHistId アップロード履歴ID
     * @return アップロード履歴モデル
     */
    Optional<UploadHistModel> selectById(Long uploadHistId);

    /**
     * 引数のアップロード履歴モデルを更新する
     *
     * @param model     アップロード履歴モデル
     * @param createdId 更新者ID
     * @return アップロード履歴モデル
     */
    UploadHistModel store(UploadHistModel model, String createdId);

    /**
     * 引数の検索条件で取得する
     *
     * @param uploadType   アップロード種別
     * @param uploadStatus アップロードステータス
     * @param siteType     サイト種別
     * @param createdId    実行者ID
     * @param orderBy      ソート順
     * @param options      検索オプション
     * @return 検索結果
     */
    SearchResult<UploadHistModel> selectBySearchCond(String uploadType, String uploadStatus,
                                                     String siteType, String createdId,
                                                     String orderBy, SelectOptions options);

    /**
     * 引数の検索条件で取得する
     *
     * @param uploadType   アップロード種別
     * @param uploadStatus アップロードステータス
     * @param siteType     サイト種別
     * @param createdId    実行者ID
     * @param createdIds   実行者IDリスト
     * @param orderBy      ソート順
     * @param options      検索オプション
     * @return 検索結果
     */
    SearchResult<UploadHistModel> selectBySearchCond(String uploadType, String uploadStatus,
                                                     String siteType, String createdId,
                                                     List<String> createdIds, String orderBy,
                                                     SelectOptions options);

    /**
     * 引数の保管期間よりも登録日時が前のアップロードデータを取得する
     *
     * @param storageTerm 保管期間
     * @return アップロード履歴モデルリスト
     */
    List<UploadHistModel> selectBeforeDayDate(LocalDateTime storageTerm);

    /**
     * 引数のアップロード種別の最新のレコードを取得する
     *
     * @param uploadType アップロード種別
     * @return アップロード履歴モデル
     */
    Optional<UploadHistModel> selectLatestByUploadType(UploadType uploadType);

    /**
     * アップロード履歴を物理削除する
     *
     * @param uploadHistIds 削除対象アップロード履歴IDリスト
     */
    void deletePhysical(List<Long> uploadHistIds);
}
