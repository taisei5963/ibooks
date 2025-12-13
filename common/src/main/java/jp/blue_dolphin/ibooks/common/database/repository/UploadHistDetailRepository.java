package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.UploadHistDetailModel;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * アップロード履歴詳細リポジトリ
 */
@Repository
public interface UploadHistDetailRepository {
    /**
     * 引数の検索条件で取得する
     *
     * @param detailUploadHistId アップロード履歴ID
     * @param orderBy            ソート順
     * @param options            検索オプション
     * @return 検索結果
     */
    SearchResult<UploadHistDetailModel> selectByUploadHistId(Long detailUploadHistId,
                                                             String orderBy, SelectOptions options);

    /**
     * アップロード履歴詳細を物理削除
     *
     * @param uploadHistIds 削除対象アップロード履歴IDリスト
     */
    void deletePhysicalDetail(List<Long> uploadHistIds);

    /**
     * 引数のアップロード履歴詳細モデルリストを登録する
     *
     * @param list アップロード履歴詳細モデルリスト
     */
    void uploadInsert(List<UploadHistDetailModel> list);
}
