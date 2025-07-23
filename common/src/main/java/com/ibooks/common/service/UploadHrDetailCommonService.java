package com.ibooks.common.service;

import com.ibooks.common.database.repository.UploadHrDetailRepository;
import com.ibooks.common.exception.SystemException;
import com.ibooks.common.model.UploadHrDetailModel;
import com.ibooks.common.model.UploadHrModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class UploadHrDetailCommonService {

    /** ログ */
    private static final Logger log = LoggerFactory.getLogger(UploadHrDetailCommonService.class);

    /** アップロード履歴詳細レポジトリ */
    private UploadHrDetailRepository uploadHrDetailRepository;

    /**
     * アップロード履歴詳細を物理削除
     *
     * @param uploadHrIdList 削除対象のアップロード履歴IDリスト
     */
    public void deletePhysicalDetail(List<Long> uploadHrIdList) {
        uploadHrDetailRepository.deletePhysicalDetail(uploadHrIdList);
    }

    /**
     * 引数のエラーメッセージリストをアップロード履歴詳細に登録
     *
     * @param uploadHrModel アップロード履歴モデル
     * @param errorMessages エラーメッセージリスト
     */
    public void insertErrorDetails(UploadHrModel uploadHrModel, List<String> errorMessages) {
        if (Objects.isNull(errorMessages) || errorMessages.isEmpty()) {
            return;
        }
        if (Objects.isNull(uploadHrModel)) {
            throw new SystemException("引数のアップロード履歴が NULL です");
        }
        LocalDateTime now = LocalDateTime.now();
        List<UploadHrDetailModel> models = new ArrayList<>();
        for (String errorMsg : errorMessages) {
            UploadHrDetailModel detail =
                    UploadHrDetailModel.builder()
                            .uploadHrId(uploadHrModel.getUploadHrId())
                            .loglevel("WARN")
                            .content(errorMsg)
                            .createdAt(now)
                            .build();
            models.add(detail);
        }
        uploadHrDetailRepository.batchInsert(models);
    }
}
