package com.ibooks.common.database.repository;

import com.ibooks.common.database.dao.UploadHrDetailDao;
import com.ibooks.common.database.entity.UploadHrDetail;
import com.ibooks.common.model.UploadHrDetailModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * アップロード履歴詳細レポジトリ
 */
@AllArgsConstructor
@Repository
public class UploadHrDetailRepositoryImpl implements UploadHrDetailRepository {
    /** アップロード履歴詳細DAO */
    private UploadHrDetailDao uploadHrDetailDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void batchInsert(List<UploadHrDetailModel> list) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return;
        }
        List<UploadHrDetail> entities = new ArrayList<>();
        for (UploadHrDetailModel model : list) {
            entities.add(convertEntity(model));
        }
        uploadHrDetailDao.insert(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhysicalDetail(List<Long> uploadHrIdList) {
        uploadHrDetailDao.deletePhysicalDetail(uploadHrIdList);
    }

    /**
     * 引数のアップロード履歴詳細モデルをアップロード履歴詳細エンティティに変換する
     *
     * @param uploadHrDetailModel アップロード履歴詳細モデル
     * @return アップロード履歴詳細エンティティ
     */
    private UploadHrDetail convertEntity(UploadHrDetailModel uploadHrDetailModel) {
        if (Objects.isNull(uploadHrDetailModel)) {
            return null;
        }
        return UploadHrDetail.builder()
                .uploadHrDetailId(uploadHrDetailModel.getUploadHrDetailId())
                .uploadHrId(uploadHrDetailModel.getUploadHrId())
                .logLevel(uploadHrDetailModel.getLoglevel())
                .content(uploadHrDetailModel.getContent())
                .createdAt(uploadHrDetailModel.getCreatedAt())
                .build();
    }
}
