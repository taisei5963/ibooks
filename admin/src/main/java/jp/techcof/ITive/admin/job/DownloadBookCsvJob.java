package jp.techcof.ITive.admin.job;

import jp.techcof.ITive.admin.config.BookConfig;
import jp.techcof.ITive.common.csv.BookCsv;
import jp.techcof.ITive.common.database.repository.BookRepository;
import jp.techcof.ITive.common.database.repository.CategoryRepository;
import jp.techcof.ITive.common.dto.Account;
import jp.techcof.ITive.common.dto.SearchResult;
import jp.techcof.ITive.common.job.DownloadCsvJob;
import jp.techcof.ITive.common.request.SearchForm;
import jp.techcof.ITive.common.service.BookCommonService;
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
