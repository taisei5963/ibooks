package jp.blue_dolphin.ibooks.admin.controller;

import jp.blue_dolphin.ibooks.admin.dto.AdminDto;
import jp.blue_dolphin.ibooks.admin.job.UploadCategoryCsvJob;
import jp.blue_dolphin.ibooks.common.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * カテゴリアップロードコントローラー
 */
@AllArgsConstructor
@Controller
@RequestMapping("/categoryUpload")
public class CategoryUploadController {
    /** CSVアップロードサービス */
    private UploadCsvService uploadCsvService;
    /** カテゴリCSVアップロードジョブ */
    private UploadCategoryCsvJob uploadCategoryCsvJob;
    /** 管理者DTO */
    private AdminDto adminDto;

    /**
     * カテゴリCSVアップロード
     *
     * @return テンプレートパス
     */
    @RequestMapping({"", "/", "index"})
    public String index() {
        return getTemplatePath();
    }

    /**
     * テンプレートパスを返却する
     *
     * @return テンプレートパス
     */
    private String getTemplatePath() {
        return "categoryUpload/index";
    }

    /**
     * CSVファイルをアップロードする<br>
     * SSEによる非同期処理
     *
     * @param file CSVファイル
     * @return SSEエミッター
     */
    @RequestMapping("upload")
    @ResponseBody
    public SseEmitter upload(@RequestParam("csvFile") MultipartFile file) {
        long timeout = 30 * 60 * 1000;
        SseEmitter emitter = new SseEmitter(timeout);
        uploadCsvService.upload(uploadCategoryCsvJob, file, emitter, adminDto.getAccount());
        return emitter;
    }
}
