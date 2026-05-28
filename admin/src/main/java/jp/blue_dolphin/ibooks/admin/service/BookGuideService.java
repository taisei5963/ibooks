package jp.blue_dolphin.ibooks.admin.service;

import jp.blue_dolphin.ibooks.common.csv.BookGuideCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookGuideRepository;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.exception.UploadException;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * ブックガイドサービス
 */
@AllArgsConstructor
@Service
public class BookGuideService {
    /** ブックガイドリポジトリ */
    private BookGuideRepository bookGuideRepository;
    /** メッセージサービス */
    private MessageService messageService;

    @Transactional
    public void saveCsv(CsvDto<BookGuideCsv> csvDto, SseEmitter emitter, String createdId) {
        for (BookGuideCsv csv : csvDto.getRows()) {
            try {
                bookGuideRepository.store(csv.toModel(), createdId);
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
