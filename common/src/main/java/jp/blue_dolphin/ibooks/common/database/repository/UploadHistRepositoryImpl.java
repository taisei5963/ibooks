package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.database.dao.UploadHistDao;
import jp.blue_dolphin.ibooks.common.database.entity.UploadHist;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.UploadHistModel;
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
 * アップロード履歴リポジトリ
 */
@AllArgsConstructor
@Repository
public class UploadHistRepositoryImpl implements UploadHistRepository {
    /** アップロード履歴DAO */
    private UploadHistDao uploadHistDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UploadHistModel> selectById(Long uploadHistId) {
        UploadHist entity = uploadHistDao.selectById(uploadHistId);
        if (Objects.isNull(entity)) {
            return Optional.empty();
        }
        return Optional.of(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadHistModel store(UploadHistModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<UploadHist> result;
        if (Objects.isNull(model.getUploadHistId())) {
            UploadHistModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = uploadHistDao.insert(convertEntity(setModel));
        } else {
            UploadHistModel setModel = model.toBuilder().updatedAt(now).build();
            result = uploadHistDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<UploadHistModel> selectBySearchCond(String uploadType, String uploadStatus,
                                                            String siteType, String createdId,
                                                            String orderBy, SelectOptions options) {
        return selectBySearchCond(uploadType, uploadStatus, siteType, createdId, List.of(), orderBy,
                options);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<UploadHistModel> selectBySearchCond(String uploadType, String uploadStatus,
                                                            String siteType, String createdId,
                                                            List<String> createdIds, String orderBy,
                                                            SelectOptions options) {
        List<UploadHist> entities =
                uploadHistDao.selectBySearchCond(uploadType, uploadStatus, siteType, createdId,
                        createdIds, orderBy, options);
        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<UploadHistModel> models = new ArrayList<>();
        for (UploadHist entity : entities) {
            models.add(convertModel(entity));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UploadHistModel> selectBeforeDayDate(LocalDateTime storageTerm) {
        List<UploadHist> entities = uploadHistDao.selectBeforeDayDate(storageTerm);
        List<UploadHistModel> models = new ArrayList<>();
        for (UploadHist entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UploadHistModel> selectLatestByUploadType(UploadType uploadType) {
        if (Objects.isNull(uploadType)) {
            return Optional.empty();
        }
        UploadHist entity = uploadHistDao.selectLatestByUploadType(uploadType);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhysical(List<Long> uploadHistIds) {
        uploadHistDao.deletePhysical(uploadHistIds);
    }

    /**
     * 引数のアップロード履歴エンティティをアップロード履歴モデルに変換する
     *
     * @param entity アップロード履歴エンティティ
     * @return アップロード履歴モデル
     */
    private UploadHistModel convertModel(UploadHist entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return UploadHistModel.builder()
                .uploadHistId(entity.uploadHistId)
                .uploadType(entity.uploadType)
                .uploadStatus(entity.uploadStatus)
                .filePath(entity.filePath)
                .fileSize(entity.fileSize)
                .rowCount(entity.rowCount)
                .importCount(entity.importCount)
                .siteType(entity.siteType)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のアップロード履歴モデルをアップロード履歴エンティティに変換する
     *
     * @param model アップロード履歴モデル
     * @return アップロード履歴エンティティ
     */
    private UploadHist convertEntity(UploadHistModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return UploadHist.builder()
                .uploadHistId(model.getUploadHistId())
                .uploadType(model.getUploadType())
                .uploadStatus(model.getUploadStatus())
                .filePath(model.getFilePath())
                .fileSize(model.getFileSize())
                .rowCount(model.getRowCount())
                .importCount(model.getImportCount())
                .siteType(model.getSiteType())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
