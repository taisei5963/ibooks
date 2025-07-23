package com.ibooks.admin.job;

import com.ibooks.admin.config.BookUploadConfig;
import com.ibooks.admin.service.BookService;
import com.ibooks.admin.service.UploadFileService;
import com.ibooks.common.constants.CsvDataType;
import com.ibooks.common.constants.SiteType;
import com.ibooks.common.constants.UploadType;
import com.ibooks.common.csv.BookCsv;
import com.ibooks.common.database.repository.BookRepository;
import com.ibooks.common.database.repository.CategoryRepository;
import com.ibooks.common.dto.Account;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.dto.TempFileDto;
import com.ibooks.common.exception.SystemException;
import com.ibooks.common.job.UploadCsvJob;
import com.ibooks.common.model.BookModel;
import com.ibooks.common.model.CategoryModel;
import com.ibooks.common.service.MessageService;
import com.ibooks.common.util.Strings;
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
        Pattern pattern = Pattern.compile("^.*\\.(zip|csv)$", Pattern.CASE_INSENSITIVE);
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
    public TempFileDto saveTemFile(MultipartFile file, Account account) {
        Path tmpFile = UploadFileService.getTempFilePath(bookUploadConfig.getTmpFileName(), account.id);
        if (file.getOriginalFilename().endsWith(".zip")) {
            return saveTempZipFile(file, tmpFile);
        }
        UploadFileService.saveTempCsvFile(file, tmpFile);
        return new TempFileDto(tmpFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execImport(CsvDto<BookCsv> csvDto, TempFileDto tempFileDto, SseEmitter emitter,
                           Account account) {
        Map<String, Path> files =
                tempFileDto.getTempImages().stream().collect(Collectors.toMap(p -> p.getFileName().toString(), p -> p));
        bookService.saveCsv(csvDto, files, emitter, account.code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> extraValidation(List<BookCsv> csvList, List<Path> imgFiles, Account account) {
        List<String> errors = new ArrayList<>();

        Map<String, Long> categoryIdMap = categoryRepository.selectAll().stream()
                .collect(Collectors.toMap(CategoryModel::getCategoryCode, CategoryModel::getCategoryId));

        Map<String, Path> files = imgFiles.stream()
                .collect(Collectors.toMap(p -> p.getFileName().toString(), p -> p));

        Set<String> checkCodes = new HashSet<>();
        Set<String> checkTitles = new HashSet<>();

        Iterator<BookCsv> iterator = csvList.iterator();

        while (iterator.hasNext()) {
            BookCsv csv = iterator.next();
            boolean hasError = false;
            CsvDataType csvDataType = CsvDataType.getEnum(csv.getCsvDataType());

            if (Strings.isEmpty(csv.getBookCode())) {
                if (csvDataType == CsvDataType.UPDATE) {
                    errors.add(
                            messageService.getMessage("csv.error.book.bookCode.required", csv.getRowNum().toString()));
                    hasError = true;
                }
            }

            if (!hasError && !Strings.isEmpty(csv.getBookCode())) {
                BookModel modelOfCode = bookRepository.selectByCode(csv.getBookCode()).orElse(null);

                if (csvDataType == CsvDataType.ADD) {
                    if (!Objects.isNull(modelOfCode)) {
                        errors.add(messageService.getMessage("csv.error.book.exists", csv.getRowNum().toString(),
                                csv.getBookCode()));
                        hasError = true;
                    } else {
                        if (Strings.isEmpty(csv.getTitle())) {
                            errors.add(messageService.getMessage("csv.error.book.title.required",
                                    csv.getRowNum().toString()));
                            hasError = true;
                        }
                        if (Strings.isEmpty(csv.getPublisher())) {
                            errors.add(messageService.getMessage("csv.error.book.publisher.required",
                                    csv.getRowNum().toString()));
                            hasError = true;
                        }
                        BookModel modelOfTitleAndPublisher =
                                bookRepository.selectByTitleAndPublisher(csv.getTitle(), csv.getPublisher())
                                        .orElse(null);
                        if (!Objects.isNull(modelOfTitleAndPublisher)) {
                            errors.add(messageService.getMessage("csv.error.book.exists.title.publisher",
                                    csv.getRowNum().toString(), csv.getTitle(), csv.getPublisher()));
                            hasError = true;
                        }
                    }
                } else {
                    if (Objects.isNull(modelOfCode)) {
                        errors.add(messageService.getMessage("csv.error.book.notExists", csv.getRowNum().toString(),
                                csv.getBookCode()));
                        hasError = true;
                    } else {
                        csv.setBookModel(modelOfCode);
                    }
                }
                if (checkCodes.contains(csv.getBookCode())) {
                    errors.add(messageService.getMessage("csv.error.duplicate", csv.getRowNum().toString(),
                            "ブックコード", csv.getBookCode()));
                    hasError = true;
                } else {
                    checkCodes.add(csv.getBookCode());
                }
            }

            if (!hasError && !Strings.isEmpty(csv.getTitle())) {
                if (checkTitles.contains(csv.getTitle())) {
                    errors.add(messageService.getMessage("csv.error.duplicate", csv.getRowNum().toString(),
                            "タイトル", csv.getTitle()));
                    hasError = true;
                } else {
                    checkTitles.add(csv.getTitle());
                }
            }

            // INFO: すべてのカテゴリが未入力の場合のみ「その他」の情報をカテゴリ１にセット
            if (!hasError && Strings.isEmpty(csv.getCategoryCode1()) && Strings.isEmpty(csv.getCategoryCode2()) &&
                    Strings.isEmpty(csv.getCategoryCode3())) {
                csv.setCategoryId1(categoryIdMap.get("99999999"));
            }

            if (!hasError && !Strings.isEmpty(csv.getCategoryCode1())) {
                Long categoryId = categoryIdMap.get(csv.getCategoryCode1());
                if (Objects.isNull(categoryId)) {
                    errors.add(messageService.getMessage("csv.error.categoryCode.notExists", csv.getRowNum().toString(),
                            "1", csv.getCategoryCode1()));
                    hasError = true;
                } else {
                    csv.setCategoryId1(categoryId);
                }
            }

            if (!hasError && !Strings.isEmpty(csv.getCategoryCode2())) {
                if (Strings.isEmpty(csv.getCategoryCode1())) {
                    errors.add(messageService.getMessage("csv.error.categoryCode.require", csv.getRowNum().toString(),
                            "1", "2"));
                    hasError = true;
                } else {
                    Long categoryId = categoryIdMap.get(csv.getCategoryCode2());
                    if (Objects.isNull(categoryId)) {
                        errors.add(messageService.getMessage("csv.error.categoryCode.notExists",
                                csv.getRowNum().toString(),
                                "2", csv.getCategoryCode2()));
                        hasError = true;
                    } else {
                        csv.setCategoryId2(categoryId);
                    }
                }
            }

            if (!hasError && !Strings.isEmpty(csv.getCategoryCode3())) {
                if (Strings.isEmpty(csv.getCategoryCode2())) {
                    errors.add(messageService.getMessage("csv.error.categoryCode.require", csv.getRowNum().toString(),
                            "2", "3"));
                    hasError = true;
                } else {
                    Long categoryId = categoryIdMap.get(csv.getCategoryCode3());
                    if (Objects.isNull(categoryId)) {
                        errors.add(messageService.getMessage("csv.error.categoryCode.notExists",
                                csv.getRowNum().toString(),
                                "3", csv.getCategoryCode3()));
                        hasError = true;
                    } else {
                        csv.setCategoryId3(categoryId);
                    }
                }
            }

            hasError = !validateBookImages(csv, files, errors) || hasError;

            if (hasError) {
                iterator.remove();
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
     * アップロードされたファイルが存在するか確認する
     *
     * @param csv    ブックCSV
     * @param files  一時画像ファイル
     * @param errors エラーメッセージリスト
     * @return {@code true} すべての画像がある
     */
    private boolean validateBookImages(BookCsv csv, Map<String, Path> files, List<String> errors) {
        boolean hasError = !validateBookImages(csv.getPicFileName(), csv.getRowNum(), files, errors);

        return !hasError;
    }

    /**
     * アップロードされた画像が存在するかどうか確認する
     *
     * @param imgFile 画像ファイル
     * @param rowNum  CSV行数
     * @param files   一時画像ファイルリスト
     * @param errors  エラーメッセージリスト
     * @return {@code true} 画像がある
     */
    private boolean validateBookImages(String imgFile, Integer rowNum, Map<String, Path> files, List<String> errors) {
        if (!Strings.isEmpty(imgFile) && !BookCsv.IMAGE_REGISTERED.equals(imgFile)) {
            if (files.get(imgFile) == null) {
                errors.add(messageService.getMessage("csv.error.book.imageFile.notExists", rowNum.toString(), imgFile,
                        String.valueOf(1)));
                return false;
            }
        }
        return true;
    }

    /**
     * アップロードされた ZIP ファイルを一時ファイルに保存する
     *
     * @param source  アップロードされたファイル
     * @param tmpFile 一時ファイル
     * @return 一時ファイルDTO
     */
    private TempFileDto saveTempZipFile(MultipartFile source, Path tmpFile) {
        List<Path> images = new ArrayList<>();
        String error = null;

        try {
            Path parent = tmpFile.getParent();
            Files.createDirectories(parent);
            Path zipFile = parent.resolve(tmpFile.getFileName().toString().replace(".csv", ".zip"));
            Path zipDir = parent.resolve(tmpFile.getFileName().toString().replace(".csv", ""));
            Files.createDirectories(zipDir);
            Files.write(zipFile, source.getBytes());
            Pattern pattern = Pattern.compile("^.*\\.(jpg|jpeg|csv)$", Pattern.CASE_INSENSITIVE);
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
                        try (FileOutputStream fos = new FileOutputStream(ucFile.toFile());
                             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                            byte[] data = new byte[1024];
                            int count;
                            while ((count = zis.read(data)) != -1) {
                                bos.write(data, 0, count);
                            }
                        }
                        if (zipEntry.getName().endsWith(".csv") || zipEntry.getName().endsWith(".CSV")) {
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
            if (!Files.exists(tmpFile)) {
                error = messageService.getMessage("errors.csv.notFound");
            }
        } catch (IOException e) {
            throw new SystemException("クライアントからアップロードされたファイルの保存に失敗しました", e);
        }
        return new TempFileDto(tmpFile, images, error);
    }
}
