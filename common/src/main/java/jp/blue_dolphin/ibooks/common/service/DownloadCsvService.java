package jp.blue_dolphin.ibooks.common.service;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanWriter;
import jakarta.servlet.http.HttpServletResponse;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.exception.DownloadFileNotFoundException;
import jp.blue_dolphin.ibooks.common.job.DownloadCsvJob;
import jp.blue_dolphin.ibooks.common.request.SearchForm;
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
 * ダウンロードCSVサービス
 */
@AllArgsConstructor
@Service
public class DownloadCsvService {
    /** ログ */
    private static final Logger logger = LoggerFactory.getLogger(DownloadCsvService.class);
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
        Path tmpPath = exportFilePath(job, account, now);
        csvCommonService.createFile(job, form, account, emitter, now, tmpPath);
    }

    /**
     * CSVファイルをダウンロードする
     *
     * @param job      CSVファイルダウンロードジョブ
     * @param account  アカウント
     * @param key      ファイル照合用キー
     * @param response HTTPレスポンス
     */
    public <T> void download(DownloadCsvJob<T> job, Account account, String key,
                             HttpServletResponse response) {
        Path tmpPath = exportFilePath(job, account, key);
        if (!Files.exists(tmpPath)) {
            throw new DownloadFileNotFoundException();
        }
        try (OutputStream os = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + tmpPath.getFileName().toString());
            response.setContentLength((int) Files.size(tmpPath));
            os.write(Files.readAllBytes(tmpPath));
            os.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * CSVライターにCSVデータを取得して書き込む
     *
     * @param csvWriter CSVライター
     * @param pageable  ページャブル
     * @param job       CSVファイルダウンロードジョブ
     * @param form      検索フォーム
     * @param account   アカウント
     * @return {@code true} 未取得データあり
     * @throws IOException 例外発生時にスローされる
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

    /**
     * CSVファイルを作成する
     *
     * @param job     CSVファイルダウンロードジョブ
     * @param form    検索フォーム
     * @param account アカウント
     * @return 作成したCSVファイルのパス
     */
    public <T> Path createFileOnly(DownloadCsvJob<T> job, SearchForm form, Account account) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
        Path tmpPath = exportFilePath(job, account, now);

        try (CsvAnnotationBeanWriter<T> csvWriter = csvCommonService.getCsvWriter(tmpPath,
                job.getCsvClass(), job.getEncode())) {
            Pageable pageable = Pageable.ofSize(1000).first();
            while (writeCsv(csvWriter, pageable, job, form, account)) {
                pageable = pageable.next();
            }
            job.afterExportRow(tmpPath, account);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return tmpPath;
    }

    /**
     * SSEエミッターに処理成功のレスポンスを返却する
     *
     * @param emitter SSEエミッター
     * @param key     ファイル照合用キー
     */
    public static void sendEmitterSuccessResponse(SseEmitter emitter, String key) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "SUCCESS");
        data.put("key", key);
        UploadCsvService.sendEmitterResponse(emitter, data);
    }

    /**
     * 処理失敗のレスポンスを返却する
     *
     * @param emitter SSEエミッター
     */
    @Async
    public void sendErrorResponse(SseEmitter emitter) {
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
}
