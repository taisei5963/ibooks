package com.ibooks.admin.controller;

import com.ibooks.admin.config.AdminConfig;
import com.ibooks.admin.request.AdminSearchForm;
import com.ibooks.admin.service.AdminService;
import com.ibooks.common.constants.SortKey;
import com.ibooks.common.dto.PageDto;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.AdminModel;
import com.ibooks.common.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者コントローラー
 */
@AllArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
    /** 管理者サービス */
    private AdminService adminService;

    /**
     * 管理者トップ
     *
     * @param form     管理者フォーム
     * @param pageable ページャブル
     * @param model    テンプレートモデル
     * @return テンプレートパス
     */
    @RequestMapping({"", "/", "index"})
    public String index(AdminSearchForm form, Pageable pageable, Model model) {
        SearchResult<AdminModel> result = adminService.getAll(form, pageable);
        if (result.isEmpty() && pageable.getPageNumber() > 0) {
            return index(form, pageable.withPage(PageDto.beforePageNum(pageable, result.getCount())), model);
        }
        model.addAttribute("page",
                PageDto.of(pageable, result.getCount(), form.getSortKey(), form.getSortValue(), form));
        model.addAttribute("admins", result.getList());
        return "admin/index";
    }
}
