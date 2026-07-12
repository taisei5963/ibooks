package jp.techcof.ITive.admin.service;

import jp.techcof.ITive.common.csv.CategoryCsv;
import jp.techcof.ITive.common.database.repository.CategoryRepository;
import jp.techcof.ITive.common.dto.CsvDto;
import jp.techcof.ITive.common.dto.IdAndName;
import jp.techcof.ITive.common.exception.UploadException;
import jp.techcof.ITive.common.service.MessageService;
import jp.techcof.ITive.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * カテゴリサービス
 */
@AllArgsConstructor
@Service
public class CategoryService {
    /** カテゴリリポジトリ */
    private CategoryRepository categoryRepository;
    /** メッセージサービス */
    private MessageService messageService;

    /**
     * カテゴリIDとカテゴリ名のリストを取得する
     *
     * @return カテゴリIDとカテゴリ名のリスト
     */
    public List<IdAndName> selectIdAndNames() {
        return categoryRepository.selectIdAndNames();
    }

    /**
     * カテゴリIDとカテゴリ名のMapを取得する
     *
     * @return カテゴリIDをキー、カテゴリ名を値とするMap
     */
    public Map<Long, String> getCategoryNameMap(List<IdAndName> categories) {
        return categories.stream()
                .collect(Collectors.toMap(IdAndName::getId, IdAndName::getName));
    }

    /**
     * ブックCSVを登録する<br>
     *
     * @param csvDto    CSV DTO
     * @param fileMap   画像ファイルマップ
     * @param emitter   Sseエミッター
     * @param createdId 担当者ID
     */
    @Transactional
    public void saveCsv(CsvDto<CategoryCsv> csvDto, Map<String, Path> fileMap, SseEmitter emitter,
                        String createdId) {
        for (CategoryCsv csv : csvDto.getRows()) {
            try {
                categoryRepository.store(csv.toModel(), createdId);
                csvDto.importSuccess();
                UploadCsvService.sendEmitterProgressResponseAuto(emitter, csvDto.getRowCount(),
                        csvDto.getImportCount());
            } catch (Exception e) {
                csvDto.appendErrorMessage(messageService.getMessage("csv.error.system.exception",
                        csv.getRowNum().toString(), e.getMessage()));
                throw new UploadException(messageService.getMessage("csv.error.exception"), e);
            }
            UploadCsvService.sendEmitterProgressResponse(emitter, csvDto.getRowCount(),
                    csvDto.getImportCount());
        }
    }
}
