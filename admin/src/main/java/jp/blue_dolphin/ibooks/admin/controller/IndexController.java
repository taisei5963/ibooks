package jp.blue_dolphin.ibooks.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * インデックスコントローラー
 */
@Controller
public class IndexController {
    /**
     * インデックスページ
     * @return テンプレートパス
     */
    @GetMapping("/")
    public String index() {
        return "redirect:book/index";
    }
}
