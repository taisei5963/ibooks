package jp.blue_dolphin.ibooks.admin.job;

import jp.blue_dolphin.ibooks.admin.config.BookChapterUploadConfig;
import jp.blue_dolphin.ibooks.admin.service.BookChapterService;
import jp.blue_dolphin.ibooks.admin.service.UploadFileService;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.csv.BookChapterCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookRepository;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.dto.TempFileDto;
import jp.blue_dolphin.ibooks.common.job.UploadCsvJob;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ブックチャプターCSVアップロードジョブ
 */
@AllArgsConstructor
@Component
public class UploadBookChapterCsvJob implements UploadCsvJob<BookChapterCsv> {
    /** ブックリポジトリ */
    private BookRepository bookRepository;
    /** ブックチャプターサービス */
    private BookChapterService bookChapterService;
    /** ブックチャプターアップロード設定 */
    private BookChapterUploadConfig bookChapterUploadConfig;
    /** メッセージサービス */
    private MessageService messageService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateUploadFile(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            return messageService.getMessage("errors.upload.notFound");
        }
        Pattern pattern = Pattern.compile(SystemRegex.FILE_EXTENSION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        if (!matcher.matches()) {
            return messageService.getMessage("errors.extension.type", "csv");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TempFileDto saveTempFile(MultipartFile file, Account account) {
        Path tmpFile =
                UploadFileService.getTmpFilePath(bookChapterUploadConfig.getTempFileName(),
                        account.id);
        UploadFileService.saveTmpCsvFile(file, tmpFile);
        return new TempFileDto(tmpFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execImport(CsvDto<BookChapterCsv> csvDto, TempFileDto tempFileDto,
                           SseEmitter emitter,
                           Account account) {
        Map<String, Path> fileMap = tempFileDto.getTmpImages().stream()
                .collect(Collectors.toMap(p -> p.getFileName().toString(), p -> p));
        bookChapterService.saveCsv(csvDto, fileMap, emitter, account.code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> extraValidation(List<BookChapterCsv> csvList, List<Path> imgFiles,
                                        SseEmitter emitter, Account account) {
        List<String> errors = new ArrayList<>();

        Iterator<BookChapterCsv> ite = csvList.iterator();
        int count = 0;
        while (ite.hasNext()) {
            BookChapterCsv csv = ite.next();
            count++;
            boolean hasError = false;

            BookModel bookModel =
                    bookRepository.selectByJanCode(csv.getJanCode()).orElse(null);
            if (Objects.isNull(bookModel)) {
                errors.add(messageService.getMessage("csv.error.book.notExists",
                        csv.getRowNum().toString(), csv.getJanCode()));
                hasError = true;
            } else {
                csv.setBookId(bookModel.getBookId());
            }

            if (hasError) {
                ite.remove();
            }

            if (count % 1000 == 0) {
                UploadCsvService.sendEmitterProgressResponse(emitter,
                        "extraValidation loop. count: " + count);
            }
        }
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<BookChapterCsv> getCsvClass() {
        return BookChapterCsv.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncode() {
        return bookChapterUploadConfig.getEncode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadType getUploadType() {
        return UploadType.BOOK_CHAPTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUploadProcessName() {
        return UploadType.BOOK_CHAPTER.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SiteType getSiteType() {
        return SiteType.ADMIN;
    }
}
