package jp.techcof.ITive.admin.service;

import jp.techcof.ITive.common.csv.BookChapterCsv;
import jp.techcof.ITive.common.database.repository.BookChapterRepository;
import jp.techcof.ITive.common.dto.CsvDto;
import jp.techcof.ITive.common.exception.UploadException;
import jp.techcof.ITive.common.model.BookChapterModel;
import jp.techcof.ITive.common.service.MessageService;
import jp.techcof.ITive.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

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
     * @param emitter   Sseエミッター
     * @param createdId 担当者ID
     */
    @Transactional
    public void saveCsv(CsvDto<BookChapterCsv> csvDto, SseEmitter emitter, String createdId) {
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

    /**
     * 引数のブックIDに紐づくブックチャプターモデルリストを返却する
     *
     * @param bookId ブックID
     * @return ブックチャプターモデルリスト
     */
    public List<BookChapterModel> selectByBookId(long bookId) {
        return bookChapterRepository.selectByBookId(bookId);
    }
}
