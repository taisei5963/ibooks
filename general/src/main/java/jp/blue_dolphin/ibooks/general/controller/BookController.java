package jp.blue_dolphin.ibooks.general.controller;

import jp.blue_dolphin.ibooks.common.constant.Level;
import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import jp.blue_dolphin.ibooks.common.dto.NextRecommendedBookDto;
import jp.blue_dolphin.ibooks.common.dto.PageDto;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.BookChapterModel;
import jp.blue_dolphin.ibooks.common.model.BookGuideModel;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.general.request.BookSearchForm;
import jp.blue_dolphin.ibooks.general.service.BookChapterService;
import jp.blue_dolphin.ibooks.general.service.BookGuideService;
import jp.blue_dolphin.ibooks.general.service.BookService;
import jp.blue_dolphin.ibooks.general.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<Level> orderedLevels =
                Arrays.asList(Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED, Level.NONE);
        Map<String, Integer> levelOrderMap = IntStream.range(0, orderedLevels.size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> orderedLevels.get(i).name(),
                        i -> i,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        Map<String, List<BookModel>> booksGroupedByLevel = result.getList().stream()
                .collect(Collectors.groupingBy(
                        book -> book.getLevel() != null ? book.getLevel() : Level.NONE.name()
                ));

        Map<String, List<BookModel>> orderedBooksGroupedByLevel =
                booksGroupedByLevel.entrySet().stream()
                        .sorted(Comparator.comparing(
                                entry -> levelOrderMap.getOrDefault(entry.getKey(),
                                        Integer.MAX_VALUE)))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue,
                                LinkedHashMap::new
                        ));

        model.addAttribute("searchForm", searchForm);
        model.addAttribute("booksGroupedByLevel", orderedBooksGroupedByLevel);
        model.addAttribute("categories", categories);
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
        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("bookChapters", bookChapters);
        model.addAttribute("bookGuide", bookGuideOpt);
        model.addAttribute("nextRecommendedBooks", nextRecommendedBooks);
        return "book/detail";
    }
}
