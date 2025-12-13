package jp.blue_dolphin.ibooks.admin.service;

import jp.blue_dolphin.ibooks.common.config.BooksImageConfig;
import jp.blue_dolphin.ibooks.common.csv.BookCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookRepository;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.exception.SystemException;
import jp.blue_dolphin.ibooks.common.exception.UploadException;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.service.BookCommonService;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import jp.blue_dolphin.ibooks.common.util.Strings;
import jp.blue_dolphin.ibooks.admin.request.BookSearchForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private BooksImageConfig booksImageConfig;
    /** メッセージサービス */
    private MessageService messageService;

    /**
     * 引数のブックIDのブックを取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    public Optional<BookModel> selectById(Long bookId) {
        return bookCommonService.selectById(bookId);
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
    public void saveCsv(CsvDto<BookCsv> csvDto, Map<String, Path> fileMap, SseEmitter emitter,
                        String createdId) {
        Optional<Path> optDir = booksImageConfig.getUploadDir();
        for (BookCsv csv : csvDto.getRows()) {
            try {
                BookModel model = bookRepository.store(csv.toModel(), createdId);
                if (optDir.isPresent()) {
                    model = copyImageFile(model, csv, optDir.get(), fileMap);
                    bookRepository.store(model, createdId);
                }
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
     * 引数の検索フォームを条件にブックを検索する
     *
     * @param searchForm ブック検索フォーム
     * @param pageable   ページャブル
     * @return 検索結果
     */
    @Transactional
    public SearchResult<BookModel> search(BookSearchForm searchForm, Pageable pageable) {
        return bookRepository.selectBySearchCond(
                searchForm.getLikeParam("schTitle"),
                searchForm.getLikeParam("schAuthor"),
                searchForm.getLikeParam("schPublisher"),
                searchForm.getLongParam("schCategory"),
                searchForm.getOrderBy(), searchForm.getSelectOptions(pageable));
    }

    /**
     * ブックの画像ファイルをコピーする
     *
     * @param model   ブックモデル
     * @param csv     ブックCSV
     * @param dir     保存先ディレクトリ
     * @param fileMap 一時画像ファイルマップ
     * @return ブックモデル
     */
    private BookModel copyImageFile(BookModel model, BookCsv csv, Path dir,
                                    Map<String, Path> fileMap) {
        return model.toBuilder()
                .picFileName(copyImageFile(model.getBookId(), 1, csv.getPicFileName(),
                        model.getPicFileName(), dir, fileMap))
                .build();
    }

    /**
     * ブックの画像ファイルをコピーする<br>
     * CSVの画像ファイル名が空の場合は既存の画像ファイルを削除する
     *
     * @param bookId         ブックID
     * @param picNo          画像番号
     * @param csvImageFile   CSVの画像ファイル名
     * @param modelImageFile データベースの画像ファイル名
     * @param dir            保存先ディレクトリ
     * @param fileMap        一時画像ファイルマップ
     * @return 保存した画像ファイル名
     */
    private String copyImageFile(Long bookId, int picNo, String csvImageFile, String modelImageFile,
                                 Path dir, Map<String, Path> fileMap) {
        if (bookId == null || dir == null || fileMap == null) {
            return null;
        }
        if (Strings.isEmpty(csvImageFile)) {
            deleteImageFile(bookId, modelImageFile, dir);
            return null;
        }
        if (BookCsv.IMAGE_REGISTERED.equals(csvImageFile)) {
            return modelImageFile;
        }
        Path imgPath = fileMap.get(csvImageFile);
        if (Objects.nonNull(imgPath)) {
            String newImgFile = BookModel.createNewImageFileName(bookId, csvImageFile, picNo);
            Path hierarchyDir = booksImageConfig.getHierarchyImgDir(bookId).orElse(null);
            Path to;
            if (hierarchyDir == null) {
                to = dir.resolve(newImgFile);
            } else {
                to = dir.resolve(hierarchyDir).resolve(newImgFile);
            }
            try {
                deleteImageFile(bookId, modelImageFile, dir);
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
    private void deleteImageFile(Long bookId, String modelImgFile, Path dir) {
        if (!Strings.isEmpty(modelImgFile)) {
            Path hierarchyDir = booksImageConfig.getHierarchyImgDir(bookId).orElse(null);
            Path modelImgPath;
            if (hierarchyDir == null) {
                modelImgPath = dir.resolve(modelImgFile);
            } else {
                modelImgPath = dir.resolve(hierarchyDir).resolve(modelImgFile);
            }
            try {
                Files.deleteIfExists(modelImgPath);
            } catch (IOException e) {
                throw new SystemException("画像ファイルの削除に失敗しました。", e);
            }
        }
    }
}
