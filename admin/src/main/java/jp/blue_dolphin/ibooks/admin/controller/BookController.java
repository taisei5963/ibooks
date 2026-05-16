package jp.blue_dolphin.ibooks.admin.controller;

import jakarta.servlet.http.HttpServletResponse;
import jp.blue_dolphin.ibooks.admin.dto.AdminDto;
import jp.blue_dolphin.ibooks.admin.job.DownloadBookCsvJob;
import jp.blue_dolphin.ibooks.admin.request.BookSearchForm;
import jp.blue_dolphin.ibooks.admin.service.BookService;
import jp.blue_dolphin.ibooks.admin.service.CategoryService;
import jp.blue_dolphin.ibooks.admin.service.ReviewService;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import jp.blue_dolphin.ibooks.common.dto.PageDto;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.model.ReviewModel;
import jp.blue_dolphin.ibooks.common.service.DownloadCsvService;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ブックコントローラー
 */
@AllArgsConstructor
@RequestMapping("/book")
@Controller
public class BookController {

    /** ブックサービス */
    private BookService bookService;
    /** カテゴリサービス */
    private CategoryService categoryService;
    /** CSVファイルダウンロードサービス */
    private DownloadCsvService downloadCsvService;
    /** ブックCSVファイルダウンロードジョブ */
    private DownloadBookCsvJob downloadBookCsvJob;
    /** メッセージサービス */
    private MessageService messageService;
    /** レビューサービス */
    private ReviewService reviewService;
    /** 管理者DTO */
    private AdminDto adminDto;

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
     * @param redirectAttributes リダイレクト
     * @return テンプレートパス
     */
    @RequestMapping("/detail/{bookId}")
    public String detail(@PathVariable Long bookId, Model model,
                         RedirectAttributes redirectAttributes) {
        Optional<BookModel> bookOpt = bookService.selectById(bookId);
        if (bookOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", Collections.singletonList(
                    messageService.getMessage("error.search.empty", "ブック")));
            return "redirect:/book/search";
        }

        List<IdAndName> categories = categoryService.selectIdAndNames();
        Map<Long, String> categoryMap = categoryService.getCategoryNameMap(categories);
        List<ReviewModel> reviews = reviewService.selectByIdAndBookId(null, bookId);
        model.addAttribute("book", bookOpt.get());
        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("reviews", reviews);
        return "book/detail";
    }

    /**
     * CSVファイルを作成する
     *
     * @param form 検索フォーム
     * @return SSEエミッター
     */
    @PostMapping("createFile")
    @ResponseBody
    public SseEmitter createFile(BookSearchForm form) {
        long timeout = 30 * 60 * 1000;
        SseEmitter emitter = new SseEmitter(timeout);
        downloadCsvService.createFile(downloadBookCsvJob, form, null, emitter);
        return emitter;
    }

    /**
     * CSVファイルをダウンロードする
     *
     * @param key  ファイルの照合キー
     * @param resp HTTPレスポンス
     */
    @PostMapping("download")
    public void download(@RequestParam("key") String key, HttpServletResponse resp,
                         Account account) {
        downloadCsvService.download(downloadBookCsvJob, account, key, resp);
    }
}
