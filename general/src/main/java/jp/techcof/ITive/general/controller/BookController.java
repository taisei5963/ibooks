package jp.techcof.ITive.general.controller;

import jp.techcof.ITive.common.dto.IdAndName;
import jp.techcof.ITive.common.dto.NextRecommendedBookDto;
import jp.techcof.ITive.common.dto.PageDto;
import jp.techcof.ITive.common.dto.SearchResult;
import jp.techcof.ITive.common.model.BookChapterModel;
import jp.techcof.ITive.common.model.BookGuideModel;
import jp.techcof.ITive.common.model.BookModel;
import jp.techcof.ITive.common.service.MessageService;
import jp.techcof.ITive.general.request.BookSearchForm;
import jp.techcof.ITive.general.service.BookChapterService;
import jp.techcof.ITive.general.service.BookGuideService;
import jp.techcof.ITive.general.service.BookService;
import jp.techcof.ITive.general.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    /** ブックチャプターサービス */
    private BookChapterService bookChapterService;
    /** ブックガイドサービス */
    private BookGuideService bookGuideService;
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

        Map<Long, String> categoryMap = categoryService.getCategoryNameMap(categories);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("books", result.getList());
        model.addAttribute("categories", categories);
        model.addAttribute("categoryMap", categoryMap);
        return "book/index";
    }

    /**
     * ブック詳細
     *
     * @param bookId             ブックID
     * @param model              テンプレートモデル
     * @param redirectAttributes リダイレクト属性
     * @return テンプレートパス
     */
    @RequestMapping("/detail/{bookId}")
    public String detail(@PathVariable Long bookId, Model model,
                         RedirectAttributes redirectAttributes) {
        // INFO: ブック情報取得
        Optional<BookModel> bookOpt = bookService.selectById(bookId);
        if (bookOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", Collections.singletonList(
                    messageService.getMessage("error.search.empty", "ブック")));
            return "redirect:/book/search";
        }

        // INFO: ブックチャプター情報取得
        List<BookChapterModel> bookChapters = bookChapterService.selectByBookId(bookId);

        // INFO: ブックガイド情報取得
        Optional<BookGuideModel> bookGuideOpt = bookGuideService.selectByBookId(bookId);
        List<NextRecommendedBookDto> nextRecommendedBooks =
                bookGuideService.getNextRecommendedBooks(bookId);

        List<IdAndName> categories = categoryService.selectIdAndNames();
        Map<Long, String> categoryMap = categoryService.getCategoryNameMap(categories);
        model.addAttribute("book", bookOpt.get());
        model.addAttribute("categories", categories);
        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("bookChapters", bookChapters);
        model.addAttribute("bookGuide", bookGuideOpt);
        model.addAttribute("nextRecommendedBooks", nextRecommendedBooks);
        return "book/detail";
    }
}
