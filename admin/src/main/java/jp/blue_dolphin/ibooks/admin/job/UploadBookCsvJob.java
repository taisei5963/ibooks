package jp.blue_dolphin.ibooks.admin.job;

import jp.blue_dolphin.ibooks.admin.config.BookUploadConfig;
import jp.blue_dolphin.ibooks.admin.service.BookService;
import jp.blue_dolphin.ibooks.admin.service.UploadFileService;
import jp.blue_dolphin.ibooks.common.constant.CsvDataType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.csv.BookCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookRepository;
import jp.blue_dolphin.ibooks.common.database.repository.CategoryRepository;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.dto.TempFileDto;
import jp.blue_dolphin.ibooks.common.exception.SystemException;
import jp.blue_dolphin.ibooks.common.job.UploadCsvJob;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.model.CategoryModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ブックCSVアップロードジョブ
 */
@AllArgsConstructor
@Component
public class UploadBookCsvJob implements UploadCsvJob<BookCsv> {
    /** ブックリポジトリ */
    private BookRepository bookRepository;
    /** カテゴリリポジトリ */
    private CategoryRepository categoryRepository;
    /** ブックサービス */
    private BookService bookService;
    /** ブックアップロード設定 */
    private BookUploadConfig bookUploadConfig;
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
            return messageService.getMessage("errors.extension.type", "zip, csvのいずれか");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TempFileDto saveTempFile(MultipartFile file, Account account) {
        Path tmpFile =
                UploadFileService.getTmpFilePath(bookUploadConfig.getTempFileName(), account.id);
        if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".zip")) {
            return saveTempZipFile(file, tmpFile);
        }
        UploadFileService.saveTmpCsvFile(file, tmpFile);
        return new TempFileDto(tmpFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execImport(CsvDto<BookCsv> csvDto, TempFileDto tempFileDto, SseEmitter emitter,
                           Account account) {
        Map<String, Path> fileMap = tempFileDto.getTmpImages().stream()
                .collect(Collectors.toMap(p -> p.getFileName().toString(), p -> p));
        bookService.saveCsv(csvDto, fileMap, emitter, account.code);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> extraValidation(List<BookCsv> csvList, List<Path> imgFiles,
                                        SseEmitter emitter, Account account) {
        List<String> errors = new ArrayList<>();

        Map<String, Long> categoryIdMap = categoryRepository.selectAll().stream().collect(
                Collectors.toMap(CategoryModel::getCategoryCode, CategoryModel::getCategoryId));

        Map<String, Path> fileMap = imgFiles.stream()
                .collect(Collectors.toMap(p -> p.getFileName().toString(), p -> p));

        Set<String> checkCodes = new HashSet<>();
        Set<String> checkTitleAndPublishers = new HashSet<>();

        Iterator<BookCsv> ite = csvList.iterator();
        int count = 0;
        while (ite.hasNext()) {
            BookCsv csv = ite.next();
            count++;
            boolean hasError = false;
            CsvDataType csvDataType = CsvDataType.getEnum(csv.getCsvDataType());

            if (csvDataType == CsvDataType.ADD) {
                if (Strings.isEmpty(csv.getJanCode())) {
                    errors.add(messageService.getMessage("csv.error.book.janCode.required",
                            csv.getRowNum().toString()));
                    hasError = true;
                }
            }

            if (!hasError && !Strings.isEmpty(csv.getJanCode())) {
                BookModel book = bookRepository.selectByJanCode(csv.getJanCode()).orElse(null);
                if (csvDataType == CsvDataType.ADD) {
                    if (Objects.nonNull(book)) {
                        errors.add(messageService.getMessage("csv.error.book.exists",
                                csv.getRowNum().toString(), csv.getJanCode()));
                        hasError = true;
                    }
                } else {
                    if (Objects.isNull(book)) {
                        errors.add(messageService.getMessage("csv.error.book.notExists",
                                csv.getRowNum().toString(), csv.getJanCode()));
                        hasError = true;
                    }
                }
                if (checkCodes.contains(csv.getJanCode())) {
                    errors.add(messageService.getMessage("csv.error.duplicateCode",
                            csv.getRowNum().toString(), "JANコード", csv.getJanCode()));
                    hasError = true;
                } else {
                    checkCodes.add(csv.getJanCode());
                }
            }

            if (!hasError) {
                if (!Strings.isEmpty(csv.getTitle()) && !Strings.isEmpty(csv.getPublisher())) {
                    String duplicateKey = csv.getTitle() + "::" + csv.getPublisher();
                    if (checkTitleAndPublishers.contains(duplicateKey)) {
                        errors.add(messageService.getMessage(
                                "csv.error.duplicateTitleAndPublisher",
                                csv.getRowNum().toString(), csv.getTitle(), csv.getPublisher()));
                        hasError = true;
                    } else {
                        checkTitleAndPublishers.add(duplicateKey);
                    }
                    BookModel model = bookRepository.selectByTitleAndPublisher(csv.getTitle(),
                            csv.getPublisher()).orElse(null);
                    if (Objects.nonNull(model)) {
                        errors.add(messageService.getMessage("csv.error.title.publisher.exists",
                                csv.getRowNum().toString(), csv.getTitle(),
                                csv.getPublisher()));
                        hasError = true;
                    }
                }
            }

            {
                Long categoryId = categoryIdMap.get(csv.getCategoryCode1());
                if (Objects.isNull(categoryId)) {
                    errors.add(messageService.getMessage("csv.error.book.category.notExists",
                            csv.getRowNum().toString(), csv.getCategoryCode1(), "1"));
                    hasError = true;
                }
            }

            if (!Strings.isEmpty(csv.getCategoryCode2())) {
                Long categoryId = categoryIdMap.get(csv.getCategoryCode2());
                if (Objects.isNull(categoryId)) {
                    errors.add(messageService.getMessage("csv.error.book.category.notExists",
                            csv.getRowNum().toString(), csv.getCategoryCode2(), "2"));
                    hasError = true;
                }
            }

            if (!Strings.isEmpty(csv.getCategoryCode3())) {
                Long categoryId = categoryIdMap.get(csv.getCategoryCode3());
                if (Objects.isNull(categoryId)) {
                    errors.add(messageService.getMessage("csv.error.book.category.notExists",
                            csv.getRowNum().toString(), csv.getCategoryCode3(), "3"));
                    hasError = true;
                }
            }

            if (Strings.isEmpty(csv.getCategoryCode1())) {
                if (!Strings.isEmpty(csv.getCategoryCode2())) {
                    errors.add(messageService.getMessage("csv.error.book.category.required",
                            csv.getRowNum().toString(), "1", "2"));
                    hasError = true;
                }
                if (!Strings.isEmpty(csv.getCategoryCode3())) {
                    errors.add(messageService.getMessage("csv.error.book.category.required",
                            csv.getRowNum().toString(), "1", "3"));
                    hasError = true;
                }
            }

            if (Strings.isEmpty(csv.getCategoryCode2())) {
                if (!Strings.isEmpty(csv.getCategoryCode3())) {
                    errors.add(messageService.getMessage("csv.error.book.category.required",
                            csv.getRowNum().toString(), "2", "3"));
                    hasError = true;
                }
            }

            hasError = !validateImages(csv, fileMap, errors) || hasError;

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
    public Class<BookCsv> getCsvClass() {
        return BookCsv.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncode() {
        return bookUploadConfig.getEncode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadType getUploadType() {
        return UploadType.BOOK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUploadProcessName() {
        return UploadType.BOOK.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SiteType getSiteType() {
        return SiteType.ADMIN;
    }

    /**
     * アップロードされたZIPファイルを一時ファイルに保存する
     *
     * @param file    アップロードされたファイル
     * @param tmpFile 一時ファイル
     * @return 一時ファイルDTO
     */
    private TempFileDto saveTempZipFile(MultipartFile file, Path tmpFile) {
        List<Path> images = new ArrayList<>();
        String error = null;
        try {
            Path parent = tmpFile.getParent();
            Files.createDirectories(parent);
            Path zipFile = parent.resolve(tmpFile.getFileName().toString().replace(".csv", ".zip"));
            Path zipDir =
                    parent.resolve(tmpFile.getFileName().toString().toString().replace(".csv", ""));
            Files.createDirectories(zipDir);
            Files.write(zipFile, file.getBytes());
            Pattern pattern =
                    Pattern.compile(SystemRegex.TEMP_IMG_FILE_EXTENSION, Pattern.CASE_INSENSITIVE);
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.toFile()))) {
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    Path ucFile = zipDir.resolve(zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(ucFile);
                    } else {
                        Matcher matcher = pattern.matcher(zipEntry.getName());
                        if (!matcher.matches()) {
                            continue;
                        }
                        try (FileOutputStream fos = new FileOutputStream(ucFile.toFile())) {
                            BufferedOutputStream bos = new BufferedOutputStream(fos);
                            byte[] data = new byte[1024];
                            int count;
                            while ((count = zis.read(data)) != -1) {
                                bos.write(data, 0, count);
                            }
                        }
                        if (zipEntry.getName().endsWith(".csv") || zipEntry.getName()
                                .endsWith(".CSV")) {
                            if (Files.exists(tmpFile)) {
                                error = messageService.getMessage("errors.csv.multipleFound");
                            } else {
                                Files.move(ucFile, tmpFile);
                            }
                        } else {
                            images.add(ucFile);
                        }
                    }
                }
            }
            if (Files.exists(tmpFile)) {
                error = messageService.getMessage("errors.csv.notFound");
            }
        } catch (IOException e) {
            throw new SystemException("クライアントからアップロードされたファイルの保存に失敗", e);
        }
        return new TempFileDto(tmpFile, images, error);
    }

    /**
     * アップロードされた画像が存在するかどうかをチェックする
     *
     * @param csv     ブックCSV
     * @param fileMap 一時画像ファイルリスト
     * @param errors  エラーメッセージリスト
     * @return {@code true} 画像がある
     */
    private boolean validateImages(BookCsv csv, Map<String, Path> fileMap, List<String> errors) {
        boolean hasError =
                !validateImages(csv.getPicFileName(), csv.getRowNum(), fileMap, errors);
        return !hasError;
    }

    /**
     * アップロードされた画像が存在するかどうかをチェックする
     *
     * @param imgFile 画像ファイル
     * @param rowNum  CSV行数
     * @param fileMap 一時画像ファイルマップ
     * @param errors  エラーメッセージリスト
     * @return {@code true} 画像がある
     */
    private boolean validateImages(String imgFile, Integer rowNum,
                                   Map<String, Path> fileMap, List<String> errors) {
        if (!Strings.isEmpty(imgFile) && !BookCsv.IMAGE_REGISTERED.equals(imgFile)) {
            if (fileMap.get(imgFile) == null) {
                errors.add(messageService.getMessage("csv.error.book.imgFile.notExists",
                        rowNum.toString(), imgFile));
                return false;
            }
        }
        return true;
    }
}
