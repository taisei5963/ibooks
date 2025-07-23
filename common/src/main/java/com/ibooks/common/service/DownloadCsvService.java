package com.ibooks.common.service;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanWriter;
import com.ibooks.common.dto.Account;
import com.ibooks.common.exception.DownloadFileNotFoundException;
import com.ibooks.common.job.DownloadCsvJob;
import com.ibooks.common.request.SearchForm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * CSVダウンロードサービス
 */
@AllArgsConstructor
@Service
public class DownloadCsvService {
    /** ログ */
    private static final Logger log = LoggerFactory.getLogger(DownloadCsvService.class);

    /** CSV共通サービス */
    private CsvCommonService csvCommonService;

    /**
     * CSVファイルを作成する
     *
     * @param job     CSVファイルダウンロードジョブ
     * @param form    検索フォーム
     * @param account アカウント
     * @param emitter SSEエミッター
     */
    @Async
    public <T> void createFile(DownloadCsvJob<T> job, SearchForm form, Account account,
                               SseEmitter emitter) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
        Path tempPath = exportFilePath(job, account, now);
        csvCommonService.createFile(job, form, account, emitter, now, tempPath);
    }

    /**
     * CSVファイルをダウンロードする
     *
     * @param job      CSVファイルダウンロードジョブ
     * @param account  アカウント
     * @param key      ファイル照合キー
     * @param response HTTPレスポンス
     */
    public <T> void download(DownloadCsvJob<T> job, Account account, String key,
                             HttpServletResponse response) {
        Path tempPath = exportFilePath(job, account, key);
        if (!Files.exists(tempPath)) {
            throw new DownloadFileNotFoundException();
        }
        try (OutputStream os = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + tempPath.getFileName().toString());
            response.setContentLength((int) Files.size(tempPath));
            os.write(Files.readAllBytes(tempPath));
            os.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * CSVファイルを作成する
     *
     * @param job     CSVファイルダウンロードジョブ
     * @param form    検索フォーム
     * @param account アカウント
     * @return 作成したCSVファイル
     */
    public <T> Path createCsvFile(DownloadCsvJob<T> job, SearchForm form, Account account) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
        Path tempPath = exportFilePath(job, account, now);

        try (CsvAnnotationBeanWriter<T> csvWriter = csvCommonService.getCsvWriter(tempPath,
                job.getCsvClas(), job.getEncode())) {
            Pageable pageable = Pageable.ofSize(1000).first();
            while (writeCsv(csvWriter, pageable, job, form, account)) {
                pageable = pageable.next();
            }
            job.afterExport(tempPath, account);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return tempPath;
    }

    /**
     * SSEエミッターに処理成功のレスポンス返却する
     *
     * @param emitter SSEエミッター
     * @param key     ファイル照合キー
     */
    public static void sendEmitterSuccessResponse(SseEmitter emitter, String key) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "SUCCESS");
        data.put("key", key);
        UploadCsvService.sendEmitterResponse(emitter, data);
    }

    /**
     * SSEエミッターに処理失敗のレスポンスを返却する
     *
     * @param emitter SSEエミッター
     */
    @Async
    public void sendEmitterResponse(SseEmitter emitter) {
        sendEmitterErrorResponse(emitter);
        emitter.complete();
    }

    /**
     * SSEエミッターに処理失敗のレスポンスを返却する
     *
     * @param emitter SSEエミッター
     */
    public static void sendEmitterErrorResponse(SseEmitter emitter) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "ERROR");
        UploadCsvService.sendEmitterResponse(emitter, data);
    }

    /**
     * CSVライターにCSVデータを取得して書き込む
     *
     * @param csvWriter CSVライター
     * @param pageable  ページャブル
     * @param job       CSVダウンロードジョブ
     * @param form      検索フォーム
     * @param account   アカウント
     * @return {@code true} 未取得のデータあり
     */
    private <T> boolean writeCsv(CsvAnnotationBeanWriter<T> csvWriter, Pageable pageable,
                                 DownloadCsvJob<T> job, SearchForm form, Account account)
            throws IOException {
        return csvCommonService.writeCsv(csvWriter, pageable, job, form, null, account);
    }

    /**
     * 出力するファイルのパスを返却する
     *
     * @param job     CSVファイルダウンロードジョブ
     * @param account アカウント
     * @param now     現在日時
     * @return 出力するファイルのパス
     */
    private <T> Path exportFilePath(DownloadCsvJob<T> job, Account account, String now) {
        return Paths.get(job.exportDir(), job.exportFileIdentifier(),
                String.format(DownloadCsvJob.FILE_NAME, job.exportFileIdentifier(),
                        account.siteIdentifier + account.id, now));
    }
}
