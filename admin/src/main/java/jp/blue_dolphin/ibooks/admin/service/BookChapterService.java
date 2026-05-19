package jp.blue_dolphin.ibooks.admin.service;

import jp.blue_dolphin.ibooks.common.csv.BookChapterCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookChapterRepository;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.exception.UploadException;
import jp.blue_dolphin.ibooks.common.model.BookChapterModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.Map;

/**
 * ブックチャプターサービス
 */
@AllArgsConstructor
@Service
public class BookChapterService {
    /** ブックチャプターリポジトリ */
    private BookChapterRepository bookChapterRepository;
    /** メッセージサービス */
    private MessageService messageService;

    /**
     * ブックCSVを登録する<br>
     *
     * @param csvDto    CSV DTO
     * @param fileMap   画像ファイルマップ
     * @param emitter   Sseエミッター
     * @param createdId 担当者ID
     */
    @Transactional
    public void saveCsv(CsvDto<BookChapterCsv> csvDto, Map<String, Path> fileMap, SseEmitter emitter,
                        String createdId) {
        for (BookChapterCsv csv : csvDto.getRows()) {
            try {
                bookChapterRepository.store(csv.toModel(), createdId);
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
