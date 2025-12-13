package jp.blue_dolphin.ibooks.common.database.dao;

import jp.blue_dolphin.ibooks.common.constant.MaintenanceType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.database.entity.Maintenance;
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
 * メンテナンスDAO
 */
@ConfigAutowireable
@Dao
public interface MaintenanceDao {
    /**
     * 引数のメンテナンスIDを条件に取得する
     *
     * @param maintenanceId メンテナンスID
     * @return メンテナンスエンティティ
     */
    @Select
    Maintenance selectById(Long maintenanceId);

    /**
     * 引数のメンテナンスタイプのメンテナンス一覧を取得する
     *
     * @param maintenanceType メンテナンスタイプ
     * @param orderBy         ソート順
     * @param options         検索オプション
     * @return メンテナンスエンティティ一覧
     */
    @Select
    List<Maintenance> selectBySearchCond(String maintenanceType, String orderBy,
                                         SelectOptions options);

    /**
     * 引数のメンテナンスタイプのメンテナンス一覧を取得する
     *
     * @param maintenanceType メンテナンスタイプ
     * @param siteType        サイトタイプ
     * @return メンテナンス一覧
     */
    @Select
    List<Maintenance> selectByMaintenanceType(MaintenanceType maintenanceType, SiteType siteType);

    /**
     * メンテナンス中のメンテナンス一覧を取得する
     *
     * @param now      現在日時
     * @param siteType 対象サイト
     * @return メンテナンス一覧
     */
    @Select
    List<Maintenance> selectUnderMaintenance(LocalDateTime now, SiteType siteType);

    /**
     * IDとバージョンを取得する
     *
     * @param maintenanceId メンテナンスID
     * @param ver           バージョン
     * @return メンテナンスエンティティ
     */
    @Select(ensureResult = true)
    Maintenance selectByIdAndVersion(Long maintenanceId, Integer ver);

    /**
     * 登録する
     *
     * @param entity メンテナンスエンティティ
     * @return 実行結果
     */
    @Insert
    Result<Maintenance> insert(Maintenance entity);

    /**
     * 更新する
     *
     * @param entity メンテナンスエンティティ
     * @return 実行結果
     */
    @Update
    Result<Maintenance> update(Maintenance entity);

    /**
     * 削除する
     *
     * @param entity メンテナンスエンティティ
     * @return 実行結果
     */
    @Delete
    Result<Maintenance> delete(Maintenance entity);

    /**
     * 引数のメンテナンスIDのレコードを論理削除する
     *
     * @param maintenanceId メンテナンスID
     * @param now           現在日時
     * @param createdId     担当者ID
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteById(Long maintenanceId, LocalDateTime now, String createdId);
}
