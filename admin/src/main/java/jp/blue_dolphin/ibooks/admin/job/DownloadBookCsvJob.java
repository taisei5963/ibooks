package jp.blue_dolphin.ibooks.admin.job;

import jp.blue_dolphin.ibooks.admin.config.BookConfig;
import jp.blue_dolphin.ibooks.common.csv.BookCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookRepository;
import jp.blue_dolphin.ibooks.common.database.repository.CategoryRepository;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.job.DownloadCsvJob;
import jp.blue_dolphin.ibooks.common.request.SearchForm;
import jp.blue_dolphin.ibooks.common.service.BookCommonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * ブックCSVダウンロードジョブ
 */
@AllArgsConstructor
@Component
public class DownloadBookCsvJob implements DownloadCsvJob<BookCsv> {
    /** ブックリポジトリ */
    private BookRepository bookRepository;
    /** カテゴリリポジトリ */
    private CategoryRepository categoryRepository;
    /** ブック共通サービス */
    private BookCommonService bookCommonService;
    /** ブック設定 */
    private BookConfig bookConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<BookCsv> exportCsvList(SearchForm searchForm, Pageable pageable,
                                               Account account) {
        return bookRepository.selectBySearchCondForCsv(
                searchForm.getLikeParam("schTitle"),
                searchForm.getLikeParam("schAuthor"),
                searchForm.getLikeParam("schPublisher"),
                searchForm.getLongParam("schCategory"),
                searchForm.getOrderBy(), searchForm.getSelectOptions(pageable)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String exportDir() {
        return bookConfig.getOutputDir();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String exportFileIdentifier() {
        return bookConfig.getIdentifier();
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
        return bookConfig.getEncode();
    }
}
