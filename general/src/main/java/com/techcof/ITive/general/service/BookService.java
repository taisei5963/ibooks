package com.techcof.ITive.general.service;

import com.techcof.ITive.common.database.repository.BookRepository;
import com.techcof.ITive.common.dto.SearchResult;
import com.techcof.ITive.common.model.BookModel;
import com.techcof.ITive.common.service.BookCommonService;
import com.techcof.ITive.general.request.BookSearchForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 引数の検索フォームを条件にブックを検索する
     *
     * @param searchForm ブック検索フォーム
     * @param pageable   ページャブル
     * @return 検索結果
     */
    @Transactional
    public SearchResult<BookModel> search(BookSearchForm searchForm, Pageable pageable) {
        return bookRepository.selectBySearchCondForGeneral(
                searchForm.getLongParam("schCategory"),
                searchForm.getOrderBy(), searchForm.getSelectOptions(pageable));
    }
}
