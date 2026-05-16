package jp.blue_dolphin.ibooks.common.service;

import jp.blue_dolphin.ibooks.common.database.repository.UploadHistDetailRepository;
import jp.blue_dolphin.ibooks.common.exception.SystemException;
import jp.blue_dolphin.ibooks.common.model.UploadHistDetailModel;
import jp.blue_dolphin.ibooks.common.model.UploadHistModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * アップロード履歴詳細共通サービス
 */
@AllArgsConstructor
@Service
public class UploadHistDetailCommonService {
    /** アップロード履歴詳細リポジトリ */
    private UploadHistDetailRepository uploadHistDetailRepository;

    /**
     * アップロード履歴明細を物理削除する
     *
     * @param uploadHistIds 削除対象アップロード履歴IDリスト
     */
    public void deletePhysicalDetail(List<Long> uploadHistIds) {
        uploadHistDetailRepository.deletePhysicalDetail(uploadHistIds);
    }

    /**
     * 引数のエラーメッセージリストをアップロード履歴詳細に登録する
     *
     * @param uploadHist    アップロード履歴モデル
     * @param errorMessages エラーメッセージリスト
     */
    public void insertErrorDetails(UploadHistModel uploadHist, List<String> errorMessages) {
        if (Objects.isNull(errorMessages) || errorMessages.isEmpty()) {
            return;
        }
        if (Objects.isNull(uploadHist)) {
            throw new SystemException("引数のアップロード履歴が null です。");
        }
        LocalDateTime now = LocalDateTime.now();
        List<UploadHistDetailModel> models = new ArrayList<>();
        for (String errorMsg : errorMessages) {
            UploadHistDetailModel detail = UploadHistDetailModel.builder()
                    .uploadHistId(uploadHist.getUploadHistId())
                    .logLevel("WARN")
                    .content(errorMsg)
                    .createdAt(now)
                    .build();
            models.add(detail);
        }
        uploadHistDetailRepository.uploadInsert(models);
    }
}
