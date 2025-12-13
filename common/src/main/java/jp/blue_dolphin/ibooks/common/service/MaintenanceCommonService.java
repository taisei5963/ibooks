package jp.blue_dolphin.ibooks.common.service;

import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.database.repository.MaintenanceRepository;
import jp.blue_dolphin.ibooks.common.exception.MaintenanceException;
import jp.blue_dolphin.ibooks.common.model.MaintenanceModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * メンテナンス共通サービス
 */
@AllArgsConstructor
@Service
public class MaintenanceCommonService {
    /** メンテナンスリポジトリ */
    private MaintenanceRepository maintenanceRepository;

    /**
     * メンテナンス中の場合は例外エラーをスローする
     *
     * @param siteType サイトタイプ
     */
    public void checkMaintenance(SiteType siteType) {
        LocalDateTime now = LocalDateTime.now();
        List<MaintenanceModel> models = maintenanceRepository.selectUnderMaintenance(now, siteType);
        if (models.isEmpty()) {
            return;
        }
        throw new MaintenanceException(models.getFirst().getMessage());
    }
}
