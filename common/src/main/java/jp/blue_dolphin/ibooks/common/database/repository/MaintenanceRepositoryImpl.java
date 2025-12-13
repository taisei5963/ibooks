package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.constant.MaintenanceType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.database.dao.MaintenanceDao;
import jp.blue_dolphin.ibooks.common.database.entity.Maintenance;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.MaintenanceModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * メンテナンスリポジトリ
 */
@AllArgsConstructor
@Repository
public class MaintenanceRepositoryImpl implements MaintenanceRepository {
    /** メンテナンスDAO */
    private MaintenanceDao maintenanceDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MaintenanceModel> selectById(Long maintenanceId) {
        Maintenance entity = maintenanceDao.selectById(maintenanceId);
        if (Objects.isNull(entity)) {
            return Optional.empty();
        }
        return Optional.of(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<MaintenanceModel> selectBySearchCond(MaintenanceType type, String orderBy,
                                                             SelectOptions options) {
        List<Maintenance> entities =
                maintenanceDao.selectBySearchCond(type.name(), orderBy, options);
        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<MaintenanceModel> models = new ArrayList<>();
        for (Maintenance entity : entities) {
            models.add(convertModel(entity));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MaintenanceModel> selectByMaintenanceType(MaintenanceType maintenanceType,
                                                          SiteType siteType) {
        if (Objects.isNull(maintenanceType) || Objects.isNull(siteType)) {
            return Collections.emptyList();
        }
        List<Maintenance> entities =
                maintenanceDao.selectByMaintenanceType(maintenanceType, siteType);
        List<MaintenanceModel> models = new ArrayList<>();
        for (Maintenance entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MaintenanceModel> selectUnderMaintenance(LocalDateTime now, SiteType siteType) {
        List<Maintenance> entities = maintenanceDao.selectUnderMaintenance(now, siteType);
        List<MaintenanceModel> models = new ArrayList<>();
        for (Maintenance entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaintenanceModel store(MaintenanceModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Maintenance> result;
        if (Objects.isNull(model.getMaintenanceId())) {
            MaintenanceModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = maintenanceDao.insert(convertEntity(setModel));
        } else {
            MaintenanceModel setModel =
                    model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = maintenanceDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteById(Long maintenanceId, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        return maintenanceDao.deleteById(maintenanceId, now, createdId);
    }

    /**
     * 引数のメンテナンスエンティティをメンテナンスモデルに変換する
     *
     * @param entity メンテナンスエンティティ
     * @return メンテナンスモデル
     */
    private MaintenanceModel convertModel(Maintenance entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return MaintenanceModel.builder()
                .maintenanceId(entity.maintenanceId)
                .name(entity.name)
                .type(entity.type)
                .siteType(entity.siteType)
                .status(entity.status)
                .effectiveFrom(entity.effectiveFrom)
                .effectiveTo(entity.effectiveTo)
                .message(entity.message)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のメンテナンスモデルをメンテナンスエンティティに変換する
     *
     * @param model メンテナンスモデル
     * @return メンテナンスエンティティ
     */
    private Maintenance convertEntity(MaintenanceModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Maintenance.builder()
                .maintenanceId(model.getMaintenanceId())
                .name(model.getName())
                .type(model.getType())
                .siteType(model.getSiteType())
                .status(model.getStatus())
                .effectiveFrom(model.getEffectiveFrom())
                .effectiveTo(model.getEffectiveTo())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
