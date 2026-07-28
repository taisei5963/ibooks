package com.techcof.ITive.admin.job;

import com.techcof.ITive.admin.config.BookConfig;
import com.techcof.ITive.common.csv.BookCsv;
import com.techcof.ITive.common.database.repository.BookRepository;
import com.techcof.ITive.common.database.repository.CategoryRepository;
import com.techcof.ITive.common.dto.Account;
import com.techcof.ITive.common.dto.SearchResult;
import com.techcof.ITive.common.job.DownloadCsvJob;
import com.techcof.ITive.common.request.SearchForm;
import com.techcof.ITive.common.service.BookCommonService;
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
