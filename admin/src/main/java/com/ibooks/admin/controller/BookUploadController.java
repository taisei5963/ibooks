package com.ibooks.admin.controller;

import com.ibooks.admin.dto.AdminDto;
import com.ibooks.admin.job.UploadBookCsvJob;
import com.ibooks.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * ブックアップロードコントローラー
 */
@AllArgsConstructor
@RequestMapping("/bookUpload")
@Controller
public class BookUploadController {
    /** CSVファイルアップロードサービス */
    private UploadCsvService uploadCsvService;
    /** ブックCSVアップロードジョブ */
    private UploadBookCsvJob uploadBookCsvJob;
    /** 管理者DTO */
    private AdminDto adminDto;

    /**
     * ブックCSVアップロード
     *
     * @return テンプレートパス
     */
    @GetMapping({"", "/", "index"})
    public String index() {
        return getTemplatePath();
    }

    /**
     * テンプレートパスを返却する
     *
     * @return テンプレートパス
     */
    private String getTemplatePath() {
        return "bookCsvUpload/index";
    }

    @PostMapping("upload")
    @ResponseBody
    public SseEmitter upload(@RequestParam("csvFile")MultipartFile file) {
        long timeout = 30 * 60 * 1000;
        SseEmitter emitter = new SseEmitter(timeout);
        uploadCsvService.upload(uploadBookCsvJob, file, emitter, adminDto.getAccount());
        return emitter;
    }
}
