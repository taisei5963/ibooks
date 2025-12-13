package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.constant.MaintenanceType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.MaintenanceModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * メンテナンスリポジトリ
 */
@Repository
public interface MaintenanceRepository {
    /**
     * 引数のメンテナンスIDのメンテナンスモデルを取得する
     *
     * @param maintenanceId メンテナンスID
     * @return メンテナンスモデル
     */
    Optional<MaintenanceModel> selectById(Long maintenanceId);

    /**
     * 引数のメンテナンスタイプのメンテナンスモデルを取得する
     *
     * @param type    メンテンナンスタイプ
     * @param orderBy ソート順
     * @param options 検索オプション
     * @return 検索結果
     */
    SearchResult<MaintenanceModel> selectBySearchCond(MaintenanceType type, String orderBy,
                                                      SelectOptions options);

    /**
     * 引数のメンテナンスタイプのメンテナンスモデル一覧を取得する
     *
     * @param maintenanceType メンテナンスタイプ
     * @param siteType        サイトタイプ
     * @return メンテナンスモデル一覧
     */
    List<MaintenanceModel> selectByMaintenanceType(MaintenanceType maintenanceType,
                                                   SiteType siteType);

    /**
     * メンテナンス中のメンテナンスモデル一覧を取得する
     *
     * @param now      現在日時
     * @param siteType サイトタイプ
     * @return メンテナンスモデル一覧
     */
    List<MaintenanceModel> selectUnderMaintenance(LocalDateTime now, SiteType siteType);

    /**
     * 引数のメンテナンスモデルで更新する
     *
     * @param model     メンテナンスモデル
     * @param createdId 担当者ID
     * @return メンテナンスモデル
     */
    MaintenanceModel store(MaintenanceModel model, String createdId);

    /**
     * 引数のメンテナンスIDのレコードを論理削除する
     *
     * @param maintenanceId メンテナンスID
     * @param createdId     担当者ID
     * @return 削除件数
     */
    int deleteById(Long maintenanceId, String createdId);
}
