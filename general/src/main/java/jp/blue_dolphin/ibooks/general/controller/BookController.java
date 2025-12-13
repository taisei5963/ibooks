package jp.blue_dolphin.ibooks.general.controller;

import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import jp.blue_dolphin.ibooks.common.dto.PageDto;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.general.request.BookSearchForm;
import jp.blue_dolphin.ibooks.general.service.BookService;
import jp.blue_dolphin.ibooks.general.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

/**
 * ブックコントローラクラス
 */
@AllArgsConstructor
@Controller
@RequestMapping("/book")
public class BookController {
    /** ブックサービス */
    private BookService bookService;
    /** カテゴリサービス */
    private CategoryService categoryService;
    /** メッセージサービス */
    private MessageService messageService;

    /**
     * ブック一覧
     *
     * @param searchForm ブック検索フォーム
     * @param pageable   ページャブル
     * @param model      テンプレートモデル
     * @return テンプレートパス
     */
    @RequestMapping({"", "/", "index", "search"})
    public String search(BookSearchForm searchForm, Pageable pageable, Model model) {
        SearchResult<BookModel> result = bookService.search(searchForm, pageable);
        if (result.isEmpty() && pageable.getPageNumber() > 0) {
            return search(searchForm,
                    pageable.withPage(PageDto.prevPageNumber(pageable, result.getCount())), model);
        }
        model.addAttribute("page", PageDto.of(pageable, result.getCount(),
                searchForm.getSortKey(), searchForm.getSortValue(), searchForm));
        if (result.isEmpty()) {
            model.addAttribute("errors", Collections.singletonList(
                    messageService.getMessage("error.search.empty", "ブック")));
        }

        List<IdAndName> categories = categoryService.selectIdAndNames();
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("books", result.getList());
        model.addAttribute("categories", categories);
        return "book/index";
    }
}
