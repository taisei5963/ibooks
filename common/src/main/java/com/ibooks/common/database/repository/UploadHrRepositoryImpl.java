package com.ibooks.common.database.repository;

import com.ibooks.common.constants.UploadType;
import com.ibooks.common.database.dao.UploadHrDao;
import com.ibooks.common.database.entity.UploadHr;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.UploadHrModel;
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
 * アップロード履歴レポジトリ
 */
@AllArgsConstructor
@Repository
public class UploadHrRepositoryImpl implements UploadHrRepository {
    /** アップロード履歴DAO */
    private UploadHrDao uploadHrDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UploadHrModel> restore(Long uploadHrId) {
        UploadHr uploadHr = uploadHrDao.selectById(uploadHrId);
        if (uploadHr == null) {
            return Optional.empty();
        }
        return Optional.of(convertModel(uploadHr));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadHrModel store(UploadHrModel uploadHrModel, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        Result<UploadHr> result;
        if (Objects.isNull(uploadHrModel.getUploadHrId())) {
            UploadHrModel setModel =
                    uploadHrModel.toBuilder().createDate(now).updateDate(now).createId(registerId)
                            .ver(1).build();
            result = uploadHrDao.insert(convertEntity(setModel));
        } else {
            UploadHrModel setModel = uploadHrModel.toBuilder().updateDate(now).build();
            result = uploadHrDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhysical(List<Long> uploadHrIdList) {
        uploadHrDao.deletePhysical(uploadHrIdList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UploadHrModel> selectLatestByUploadType(UploadType uploadType) {
        if (Objects.isNull(uploadType)) {
            return Optional.empty();
        }
        UploadHr entity = uploadHrDao.selectLatestByUploadType(uploadType);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<UploadHrModel> selectByCond(String uploadType, String uploadStatus, String siteType, String registerId,
                                             String orderBy, SelectOptions options) {
        List<UploadHr> entities =
                uploadHrDao.selectByCond(uploadType, uploadStatus, siteType, registerId, orderBy, options);

        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<UploadHrModel> models = new ArrayList<>();
        for (UploadHr entity : entities) {
            models.add(convertModel(entity));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * 引数のアップロード履歴エンティティをアップロード履歴モデルに変換する
     *
     * @param uploadHr アップロード履歴エンティティ
     * @return アップロード履歴モデル
     */
    private UploadHrModel convertModel(UploadHr uploadHr) {
        if (Objects.isNull(uploadHr)) {
            return null;
        }
        return UploadHrModel.builder()
                .uploadHrId(uploadHr.uploadHrId)
                .uploadType(uploadHr.uploadType)
                .uploadStatus(uploadHr.uploadStatus)
                .filePath(uploadHr.filePath)
                .fileSize(uploadHr.fileSize)
                .rowCount(uploadHr.rowCount)
                .importCount(uploadHr.importCount)
                .siteType(uploadHr.siteType)
                .createDate(uploadHr.createdAt)
                .updateDate(uploadHr.updatedAt)
                .createId(uploadHr.createdId)
                .ver(uploadHr.ver)
                .build();
    }

    /**
     * 引数のアップロード履歴モデルをアップロード履歴エンティティに変換する
     *
     * @param uploadHrModel アップロード履歴モデル
     * @return アップロード履歴エンティティ
     */
    private UploadHr convertEntity(UploadHrModel uploadHrModel) {
        if (Objects.isNull(uploadHrModel)) {
            return null;
        }
        return UploadHr.builder()
                .uploadHrId(uploadHrModel.getUploadHrId())
                .uploadType(uploadHrModel.getUploadType())
                .uploadStatus(uploadHrModel.getUploadStatus())
                .filePath(uploadHrModel.getFilePath())
                .fileSize(uploadHrModel.getFileSize())
                .rowCount(uploadHrModel.getRowCount())
                .importCount(uploadHrModel.getImportCount())
                .siteType(uploadHrModel.getSiteType())
                .createdAt(uploadHrModel.getCreateDate())
                .updatedAt(uploadHrModel.getUpdateDate())
                .createdId(uploadHrModel.getCreateId())
                .ver(uploadHrModel.getVer())
                .build();
    }
}
