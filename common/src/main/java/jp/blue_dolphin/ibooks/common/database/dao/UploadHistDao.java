package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.database.entity.UploadHist;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;

import java.time.LocalDateTime;
import java.util.List;

/**
 * アップロード履歴DAO
 */
@ConfigAutowireable
@Dao
public interface UploadHistDao {
    /**
     * 引数のアップロード履歴IDを条件に取得する
     *
     * @param uploadHistId アップロード履歴ID
     * @return アップロード履歴エンティティ
     */
    @Select
    UploadHist selectById(Long uploadHistId);

    /**
     * 引数のアップロード履歴IDとバージョンを条件に取得する
     *
     * @param uploadHistId アップロード履歴ID
     * @param ver          バージョン
     * @return アップロード履歴エンティティ
     */
    @Select(ensureResult = true)
    UploadHist selectByIdAndVersion(Long uploadHistId, Integer ver);

    /**
     * 引数の検索条件で取得する
     *
     * @param uploadType   アップロード種別
     * @param uploadStatus アップロードステータス
     * @param siteType     サイト種別
     * @param createdId    実行者
     * @param createdIds   実行者リスト
     * @param orderBy      ソート順
     * @param options      検索オプション
     * @return 検索結果
     */
    @Select
    List<UploadHist> selectBySearchCond(String uploadType, String uploadStatus, String siteType,
                                        String createdId, List<String> createdIds, String orderBy,
                                        SelectOptions options);

    /**
     * 引数の保管期間よりも登録日時が前のアップロードデータを取得する
     *
     * @param storageTerm 保管期間
     * @return アップロード履歴リスト
     */
    @Select
    List<UploadHist> selectBeforeDayDate(LocalDateTime storageTerm);

    /**
     * 引数のアップロード種別の最新レコードを取得する
     *
     * @param uploadType アップロード種別
     * @return アップロード履歴エンティティ
     */
    @Select
    UploadHist selectLatestByUploadType(UploadType uploadType);

    /**
     * 登録する
     *
     * @param entity アップロード履歴エンティティ
     * @return 実行結果
     */
    @Insert
    Result<UploadHist> insert(UploadHist entity);

    /**
     * 更新する
     *
     * @param entity アップロード履歴エンティティ
     * @return 実行結果
     */
    @Update
    Result<UploadHist> update(UploadHist entity);

    /**
     * 削除する
     *
     * @param entity アップロード履歴エンティティ
     * @return 実行結果
     */
    @Delete
    Result<UploadHist> delete(UploadHist entity);

    /**
     * 引数のアップロード履歴IDリストのレコードを物理削除する
     *
     * @param uploadHistIds アップロード履歴IDリスト
     * @return 削除結果
     */
    @Delete(sqlFile = true)
    int deletePhysical(List<Long> uploadHistIds);
}
