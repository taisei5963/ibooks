package com.ibooks.admin.service;

import com.ibooks.admin.dto.AdminDto;
import com.ibooks.admin.request.UploadHrSearchForm;
import com.ibooks.common.constants.SiteType;
import com.ibooks.common.constants.UploadStatus;
import com.ibooks.common.constants.UploadType;
import com.ibooks.common.database.repository.UploadHrRepository;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.UploadHrModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * アップロード履歴サービス
 */
@AllArgsConstructor
@Service
public class UploadHrService {
    /** 管理者DTO */
    private AdminDto adminDto;
    /** アップロード履歴リポジトリ */
    private UploadHrRepository uploadHrRepository;

    /**
     * アップロード履歴に新規レコードを追加する
     *
     * @param uploadType アップロード種別
     * @param siteType   サイト種別
     * @param registerId 登録者ID
     * @return アップロード履歴モデル
     */
    public UploadHrModel insert(UploadType uploadType, SiteType siteType, String registerId) {
        UploadHrModel model =
                UploadHrModel.builder()
                        .uploadType(uploadType)
                        .uploadStatus(UploadStatus.EXECUTE)
                        .siteType(siteType)
                        .build();
        return uploadHrRepository.store(model, registerId);
    }

    /**
     * アップロード履歴をのステータスを更新する
     *
     * @param model      アップロード履歴モデル
     * @param csvDto     CSVデータ
     * @param status     アップロードステータス
     * @param registerId 登録者ID
     * @return アップロード履歴モデル
     */
    public <Z> UploadHrModel update(UploadHrModel model, CsvDto<Z> csvDto, UploadStatus status, String registerId) {
        UploadHrModel setModel = model.toBuilder()
                .uploadStatus(status)
                .filePath(!Objects.isNull(csvDto) ? csvDto.getFilePath() : "")
                .rowCount(!Objects.isNull(csvDto) ? csvDto.getRowCount() : 0)
                .importCount(!Objects.isNull(csvDto) ? csvDto.getImportCount() : 0)
                .fileSize(!Objects.isNull(csvDto) ? csvDto.fileSize() : 0)
                .build();
        return uploadHrRepository.store(setModel, registerId);
    }

    /**
     * 引数の検索条件でアップロード履歴を取得する
     *
     * @param id アップロード履歴ID
     * @return アップロード履歴リスト
     */
    public Optional<UploadHrModel> selectById(Long id) {
        return uploadHrRepository.restore(id);
    }

    /**
     * 引数の検索条件でアップロード履歴を取得する
     *
     * @param searchForm 検索フォーム
     * @param pageable   ページャブル
     * @return アップロード履歴リスト
     */
    public SearchResult<UploadHrModel> selectByCond(UploadHrSearchForm searchForm, Pageable pageable) {
        return uploadHrRepository.selectByCond(
                searchForm.getParam("schUploadType"),
                searchForm.getParam("schUploadStatus"),
                null,
                searchForm.getParam("schRegisterId"),
                searchForm.getOrderBy(),
                searchForm.getSelectOptions(pageable));
    }
}
