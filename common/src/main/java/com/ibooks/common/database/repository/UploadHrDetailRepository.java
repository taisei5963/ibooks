package com.ibooks.common.database.repository;

import com.ibooks.common.model.UploadHrDetailModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * アップロード履歴詳細レポジトリ
 */
@Repository
public interface UploadHrDetailRepository {
    /**
     * アップロード履歴詳細を物理削除
     *
     * @param uploadHrIdList 削除対象のアップロード履歴IDリスト
     */
    void deletePhysicalDetail(List<Long> uploadHrIdList);

    /**
     * 引数のアップロード履歴詳細モデルリストを取得する
     *
     * @param list アップロード履歴詳細モデルリスト
     */
    void batchInsert(List<UploadHrDetailModel> list);
}
