package jp.blue_dolphin.ibooks.admin.job;

import jp.blue_dolphin.ibooks.admin.config.CategoryUploadConfig;
import jp.blue_dolphin.ibooks.admin.service.CategoryService;
import jp.blue_dolphin.ibooks.admin.service.UploadFileService;
import jp.blue_dolphin.ibooks.common.constant.CsvDataType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.csv.CategoryCsv;
import jp.blue_dolphin.ibooks.common.database.repository.CategoryRepository;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.dto.TempFileDto;
import jp.blue_dolphin.ibooks.common.job.UploadCsvJob;
import jp.blue_dolphin.ibooks.common.model.CategoryModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * カテゴリCSVアップロードジョブ
 */
@AllArgsConstructor
@Component
public class UploadCategoryCsvJob implements UploadCsvJob<CategoryCsv> {
    /** カテゴリリポジトリ */
    private CategoryRepository categoryRepository;
    /** カテゴリサービス */
    private CategoryService categoryService;
    /** カテゴリアップロード設定 */
    private CategoryUploadConfig categoryUploadConfig;
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
                UploadFileService.getTmpFilePath(categoryUploadConfig.getTempFileName(),
                        account.id);
        UploadFileService.saveTmpCsvFile(file, tmpFile);
        return new TempFileDto(tmpFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execImport(CsvDto<CategoryCsv> csvDto, TempFileDto tempFileDto,
                           SseEmitter emitter,
                           Account account) {
        Map<String, Path> fileMap = tempFileDto.getTmpImages().stream()
                .collect(Collectors.toMap(p -> p.getFileName().toString(), p -> p));
        categoryService.saveCsv(csvDto, fileMap, emitter, account.code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> extraValidation(List<CategoryCsv> csvList, List<Path> imgFiles,
                                        SseEmitter emitter, Account account) {
        List<String> errors = new ArrayList<>();

        Set<String> checkCodes = new HashSet<>();

        Iterator<CategoryCsv> ite = csvList.iterator();
        int count = 0;
        while (ite.hasNext()) {
            CategoryCsv csv = ite.next();
            count++;
            boolean hasError = false;
            CsvDataType dataType = CsvDataType.getEnum(csv.getCsvDataType());

            CategoryModel categoryModel =
                    categoryRepository.selectByCode(csv.getCategoryCode()).orElse(null);
            if (dataType == CsvDataType.ADD) {
                if (categoryModel != null) {
                    errors.add(messageService.getMessage("csv.error.category.exists",
                            csv.getCategoryCode()));
                    hasError = true;
                }
            } else {
                if (categoryModel == null) {
                    errors.add(messageService.getMessage("csv.error.category.notExists",
                            csv.getCategoryCode()));
                    hasError = true;
                }
            }
            if (checkCodes.contains(csv.getCategoryCode())) {
                errors.add(messageService.getMessage("csv.error.category.duplicate",
                        csv.getCategoryCode()));
                hasError = true;
            } else {
                checkCodes.add(csv.getCategoryCode());
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
    public Class<CategoryCsv> getCsvClass() {
        return CategoryCsv.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncode() {
        return categoryUploadConfig.getEncode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadType getUploadType() {
        return UploadType.CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUploadProcessName() {
        return UploadType.CATEGORY.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SiteType getSiteType() {
        return SiteType.ADMIN;
    }
}
