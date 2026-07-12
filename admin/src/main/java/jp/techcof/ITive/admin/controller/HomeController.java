package jp.techcof.ITive.admin.controller;

import jp.techcof.ITive.admin.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ホームコントローラー
 */
@AllArgsConstructor
@RequestMapping("/home")
@Controller
public class HomeController {

    /** ブックサービス */
    private BookService bookService;

    /**
     * インデックスページ
     * @return テンプレートパス
     */
    @RequestMapping({"", "/", "index"})
    public String index(Model model) {
        int bookCount = bookService.countAll();

        model.addAttribute("bookCount", bookCount);
        return "home/index";
    }
}
