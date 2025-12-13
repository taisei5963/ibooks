package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.UploadHistDetailDao;
import jp.blue_dolphin.ibooks.common.database.entity.UploadHistDetail;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.UploadHistDetailModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * アップロード履歴詳細リポジトリ
 */
@AllArgsConstructor
@Repository
public class UploadHistDetailRepositoryImpl implements UploadHistDetailRepository {
    /** アップロード履歴詳細DAO */
    private UploadHistDetailDao uploadHistDetailDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<UploadHistDetailModel> selectByUploadHistId(Long detailUploadHistId,
                                                                    String orderBy,
                                                                    SelectOptions options) {
        List<UploadHistDetail> entities =
                uploadHistDetailDao.selectByUploadHistId(detailUploadHistId, orderBy, options);
        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<UploadHistDetailModel> models = new ArrayList<>();
        for (UploadHistDetail entity : entities) {
            models.add(convertModel(entity));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhysicalDetail(List<Long> uploadHistIds) {
        uploadHistDetailDao.deletePhysicalDetail(uploadHistIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadInsert(List<UploadHistDetailModel> list) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return;
        }
        List<UploadHistDetail> entities = new ArrayList<>();
        for (UploadHistDetailModel model : list) {
            entities.add(convertEntity(model));
        }
        uploadHistDetailDao.insert(entities);
    }

    /**
     * 引数のアップロード履歴詳細エンティティをアップロード履歴詳細モデルに変換する
     *
     * @param entity アップロード履歴詳細エンティティ
     * @return アップロード履歴詳細モデル
     */
    private UploadHistDetailModel convertModel(UploadHistDetail entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return UploadHistDetailModel.builder()
                .uploadHistDetailId(entity.uploadHistDetailId)
                .uploadHistId(entity.uploadHistId)
                .logLevel(entity.logLevel)
                .content(entity.content)
                .createdAt(entity.createdAt)
                .build();
    }

    /**
     * 引数のアップロード履歴詳細モデルをアップロード履歴詳細エンティティに変換する
     *
     * @param model アップロード履歴詳細モデル
     * @return アップロード履歴詳細エンティティ
     */
    private UploadHistDetail convertEntity(UploadHistDetailModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return UploadHistDetail.builder()
                .uploadHistDetailId(model.getUploadHistDetailId())
                .uploadHistId(model.getUploadHistId())
                .logLevel(model.getLogLevel())
                .content(model.getContent())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
