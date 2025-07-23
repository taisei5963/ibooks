package com.ibooks.admin.service;

import com.ibooks.common.config.BookImageConfig;
import com.ibooks.common.csv.BookCsv;
import com.ibooks.common.database.repository.BookRepository;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.exception.SystemException;
import com.ibooks.common.exception.UploadException;
import com.ibooks.common.model.BookModel;
import com.ibooks.common.service.BookCommonService;
import com.ibooks.common.service.MessageService;
import com.ibooks.common.service.SequenceManagerService;
import com.ibooks.common.service.UploadCsvService;
import com.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * ブックサービス
 */
@AllArgsConstructor
@Service
public class BookService {
    /** ブック共通サービス */
    private BookCommonService bookCommonService;
    /** ブックリポジトリ */
    private BookRepository bookRepository;
    /** ブック画像設定 */
    private BookImageConfig bookImageConfig;
    /** メッセージサービス */
    private MessageService messageService;
    /** シーケンスサービス */
    private SequenceManagerService sequenceManagerService;

    /**
     * 引数のブックIDのブックモデルを取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    public Optional<BookModel> selectById(Long bookId) {
        return bookCommonService.selectById(bookId);
    }

    /**
     * ブックCSVを登録する<br>
     * エラーが発生した場合はロールバッさせる
     *
     * @param csvDto     CSV DTO
     * @param files      一時画像ファイルマップ
     * @param emitter    SSEエミッター
     * @param registerID 登録者ID
     */
    @Transactional
    public void saveCsv(CsvDto<BookCsv> csvDto, Map<String, Path> files,
                        SseEmitter emitter, String registerID) {
        Optional<Path> optDir = bookImageConfig.getUploadDir();
        for (BookCsv csv : csvDto.getRows()) {
            try {
                BookModel model = bookRepository.store(csv.convertModel(), registerID);
                if (optDir.isPresent()) {
                    model = copyImgFile(model, csv, optDir.get(), files);
                    bookRepository.store(model, registerID);
                }
                csvDto.importSuccess();
                UploadCsvService.sendEmitterProgressResponse(emitter, csvDto.getRowCount(), csvDto.getImportCount());
            } catch (Exception e) {
                csvDto.appendErrorMessage(
                        messageService.getMessage("csv.error.system.exception", csv.getRowNum().toString(),
                                e.getMessage()));
                throw new UploadException(messageService.getMessage("csv.error.exception"), e);
            }
            UploadCsvService.sendEmitterProgressResponse(emitter, csvDto.getRowCount(), csvDto.getImportCount());
        }
    }

    /**
     * ブックの画像ファイルをコピーする
     *
     * @param model ブックモデル
     * @param csv   ブックCSV
     * @param dir   保存先ディレクトリ
     * @param files 一時画像ファイルマップ
     * @return ブックモデル
     */
    private BookModel copyImgFile(BookModel model, BookCsv csv, Path dir, Map<String, Path> files) {
        return model.toBuilder()
                .picFileName(copyImgFile(model.getBookId(), csv.getPicFileName(),
                        model.getPicFileName(), dir, files))
                .build();
    }

    /**
     * ブックの画像ファイルをコピーする<br>
     * CSVの画像ファイル名が空の場合は既存の画像ファイルを削除する
     *
     * @param bookId       ブックID
     * @param csvImgFile   CSVのファ像ファイル名
     * @param modelImgFile データベースの画像ファイル名
     * @param dir          保存先ディレクトリ
     * @param files        一時画像ファイルマップ
     * @return 保存した画像ファイル名
     */
    private String copyImgFile(Long bookId, String csvImgFile, String modelImgFile, Path dir,
                               Map<String, Path> files) {
        if (Objects.isNull(bookId) || Objects.isNull(dir) || Objects.isNull(files)) {
            return null;
        }
        if (Strings.isEmpty(csvImgFile)) {
            return null;
        }
        if (BookCsv.IMAGE_REGISTERED.equals(csvImgFile)) {
            return modelImgFile;
        }

        Path imgPath = files.get(csvImgFile);
        if (!Objects.isNull(imgPath)) {
            String newImgFile = BookModel.generateNewImgFile(bookId, csvImgFile, 1);
            Path hierarchyDir = bookImageConfig.getHierarchyImgDir(bookId).orElse(null);
            Path to;
            if (Objects.isNull(hierarchyDir)) {
                to = dir.resolve(newImgFile);
            } else {
                to = dir.resolve(hierarchyDir).resolve(newImgFile);
            }
            try {
                deleteImgFile(bookId, modelImgFile, dir);
                Files.createDirectories(to.getParent());
                Files.copy(imgPath, to, StandardCopyOption.REPLACE_EXISTING);
                return newImgFile;
            } catch (IOException e) {
                throw new SystemException("画像ファイルの保存に失敗しました。", e);
            }
        }
        return null;
    }

    /**
     * アップロード済みの画像ファイルを削除する
     *
     * @param bookId       ブックID
     * @param modelImgFile データベースの画像ファイル名
     * @param dir          保存先ディレクトリ
     */
    private void deleteImgFile(Long bookId, String modelImgFile, Path dir) {
        if (!Strings.isEmpty(modelImgFile)) {
            Path hierarchyDir = bookImageConfig.getHierarchyImgDir(bookId).orElse(null);
            Path modelImgPath;
            if (Objects.isNull(hierarchyDir)) {
                modelImgPath = dir.resolve(modelImgFile);
            } else {
                modelImgPath = dir.resolve(hierarchyDir).resolve(modelImgFile);
            }
            try {
                Files.deleteIfExists(modelImgPath);
            } catch (IOException e) {
                throw new SystemException("画像ファイルの削除に失敗しました", e);
            }
        }
    }
}
