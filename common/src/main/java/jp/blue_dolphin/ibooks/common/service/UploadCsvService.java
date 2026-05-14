package jp.blue_dolphin.ibooks.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mygreen.supercsv.io.CsvAnnotationBeanReader;
import jp.blue_dolphin.ibooks.common.constant.UploadStatus;
import jp.blue_dolphin.ibooks.common.csv.CsvRow;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.dto.TempFileDto;
import jp.blue_dolphin.ibooks.common.exception.SystemException;
import jp.blue_dolphin.ibooks.common.exception.UploadException;
import jp.blue_dolphin.ibooks.common.job.UploadCsvJob;
import jp.blue_dolphin.ibooks.common.model.UploadHistModel;
import jp.blue_dolphin.ibooks.common.util.FileUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSVアップロードサービス
 */
@AllArgsConstructor
@Service
public class UploadCsvService {
    /** ログ */
    private static final Logger logger = LoggerFactory.getLogger(UploadCsvService.class);

    /** CSV共通サービス */
    private CsvCommonService csvCommonService;
    /** アップロード履歴共通サービス */
    UploadHistCommonService uploadHistCommonService;
    /** アップロード履歴詳細共通サービス */
    UploadHistDetailCommonService uploadHistDetailCommonService;
    /** メッセージサービス */
    MessageService messageService;

    /**
     * アップロードファイルの整合性をチェックする<br>
     * ファイル指定、拡張子などファイルの中身以外のチェックを行う
     *
     * @param job  ファイルインポートジョブ
     * @param file 処理対象のアップロードファイル
     * @return アップロードファイルチェック実行結果
     */
    public <C> String validateUploadFile(UploadCsvJob<C> job, MultipartFile file) {
        return job.validateUploadFile(file);
    }

    /**
     * ファイルアップロード処理を起動し、アップロードエラー発生時は画面への表示制御を行う
     *
     * @param job     アップロードジョブ
     * @param file    アップロードファイル
     * @param emitter SSEエミッター
     * @param account アカウント
     */
    @Async
    public <C extends CsvRow> void upload(UploadCsvJob<C> job, MultipartFile file,
                                          SseEmitter emitter, Account account) {
        try {
            executeUpdate(job, file, emitter, account);
        } catch (UploadException e) {
            logger.error(e.getMessage());
            List<String> uploadErrors = new ArrayList<>();
            if (e.getDetailMessage() != null) {
                uploadErrors.add(e.getDetailMessage());
            }
            if (!e.getDetailMessageList().isEmpty()) {
                uploadErrors.addAll(e.getDetailMessageList());
            }
            if (!uploadErrors.isEmpty()) {
                sendEmitterErrorResponse(emitter, e.getMessage(), uploadErrors);
            } else {
                sendEmitterErrorResponse(emitter, e.getMessage());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            sendEmitterErrorResponse(emitter, messageService.getMessage("csv.error.exception"));
        } finally {
            emitter.complete();
        }
    }

    /**
     * ファイルをアップロードする<br>
     * 指定されたアップロードファイルを一時ディレクトリに保存、読み込みを行い、インポート先にデータを送る
     *
     * @param job     ファイルインポートジョブ
     * @param file    処理対象のアップロードファイル
     * @param emitter SSEエミッター
     * @param account アカウント
     */
    public <C extends CsvRow> void executeUpdate(UploadCsvJob<C> job, MultipartFile file,
                                                 SseEmitter emitter, Account account) {
        String msg = null;
        {
            UploadHistModel uploadHist =
                    uploadHistCommonService.selectLatest(job.getUploadType()).orElse(null);
            if (uploadHist != null && uploadHist.getUploadStatus() == UploadStatus.EXECUTE) {
                throw new UploadException(job.getUploadType()
                        .getDescription() + "は他プロセスにてアップロード処理を実行中です。");
            }
        }
        String validateFileName = null;
        try {
            job.getClass().getDeclaredMethod("validateUploadFile", MultipartFile.class);
            validateFileName = validateUploadFile(job, file);
        } catch (NoSuchMethodException e) {
            validateFileName = defaultValidateUploadFile(file);
        }
        UploadHistModel uploadHist =
                uploadHistCommonService.insert(job.getUploadType(), job.getSiteType(),
                        account.code);
        UploadStatus uploadStatus = UploadStatus.SUCCESS;

        CsvDto<C> csvDto = null;
        try {
            if (validateFileName != null) {
                uploadStatus = UploadStatus.ERROR;
                throw new UploadException(validateFileName);
            }
            TempFileDto tempFileDto;
            try {
                tempFileDto = job.saveTempFile(file, account);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                uploadStatus = UploadStatus.ERROR;
                throw new UploadException(
                        messageService.getMessage("error.csv.zip.parse"),
                        messageService.getMessage("errors.csv.zip.read"));
            }
            if (tempFileDto.getError() != null) {
                uploadStatus = UploadStatus.ERROR;
                throw new UploadException(tempFileDto.getError());
            }
            try {
                csvDto = readCsv(job, tempFileDto, account, emitter);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } catch (SystemException e) {
                logger.error(e.getMessage(), e);
                uploadStatus = UploadStatus.ERROR;
                throw new UploadException(messageService.getMessage("errors.csv.emitter.timeout"));
            }
            if (Objects.isNull(csvDto)) {
                uploadStatus = UploadStatus.ERROR;
                throw new UploadException(
                        messageService.getMessage("errors.csv.parse.non.effective.row"),
                        messageService.getMessage("errors.csv.read"));
            }
            if (csvDto.isEmpty()) {
                if (csvDto.hasError()) {
                    uploadStatus = UploadStatus.ERROR;
                    throw new UploadException(
                            messageService.getMessage("errors.csv.parse.non.effective.row"),
                            csvDto.getErrorMessages());
                } else {
                    uploadStatus = UploadStatus.ERROR;
                    throw new UploadException(messageService.getMessage("errors.search.notFound"));
                }
            }
            if (csvDto.hasError()) {
                uploadStatus = UploadStatus.ERROR;
                throw new UploadException(
                        messageService.getMessage("errors.csv.parse"), csvDto.getErrorMessages());
            }
            try {
                job.beforeImport(csvDto, tempFileDto, emitter);
                if (job.enableSort()) {
                    csvDto.getRows().sort(Comparator.comparing(CsvRow::getSortValue));
                }
                job.execImport(csvDto, tempFileDto, emitter, account);
                List<String> warnMessages = csvDto.getWarnMessages();
                if (!warnMessages.isEmpty()) {
                    msg = messageService.getMessage("message.warn.success.upload.update",
                            job.getUploadProcessName());
                } else {
                    msg = messageService.getMessage("message.success.upload.update",
                            job.getUploadProcessName());
                }
                try {
                    if (tempFileDto.getTmpImages() != null) {
                        Path dir = null;
                        for (Path tmpPath : tempFileDto.getTmpImages()) {
                            Files.deleteIfExists(tmpPath);
                            if (Objects.isNull(dir)) {
                                dir = tmpPath.getParent();
                            }
                        }
                        if (Objects.nonNull(dir)) {
                            Files.deleteIfExists(dir);
                        }
                    }
                } catch (IOException e) {
                    uploadStatus = UploadStatus.WARN;
                    msg += messageService.getMessage("message.warn.success.update.imageDelete");
                }
                job.afterImport(csvDto, emitter);
                if (!warnMessages.isEmpty()) {
                    uploadStatus = UploadStatus.WARN;
                    UploadCsvService.sendEmitterSuccessResponse(emitter, msg, warnMessages);
                } else {
                    UploadCsvService.sendEmitterSuccessResponse(emitter, msg);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                uploadStatus = UploadStatus.SYSTEM_ERROR;
                throw e;
            }
        } finally {
            if (Objects.nonNull(uploadHist)) {
                uploadHistCommonService.update(uploadHist, csvDto, uploadStatus, account.code);
                if (Objects.nonNull(csvDto)) {
                    uploadHistDetailCommonService.insertErrorDetails(uploadHist,
                            csvDto.getAllMessages());
                }
            }
        }
    }

    /**
     * アップロードファイルのチェック（ファイル形式に関するチェック）を行う
     *
     * @param file アップロードファイル
     * @return チェック結果エラーコード（発生時のみ返却する）
     */
    private String defaultValidateUploadFile(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            return messageService.getMessage("errors.upload.notFound");
        }
        Pattern pattern = Pattern.compile("^.*\\.(csv)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        if (!matcher.matches()) {
            return messageService.getMessage("errors.extension.type", "csv");
        }
        return null;
    }

    /**
     * 引数のパスのCSVファイルを読み込む
     *
     * @param job     CSVファイルインポートジョブ
     * @param fileDto ファイルDTO
     * @param account アカウント
     * @param emitter SSEエミッター
     * @return CSV読込結果
     */
    private <C extends CsvRow> CsvDto<C> readCsv(UploadCsvJob<C> job, TempFileDto fileDto,
                                                 Account account, SseEmitter emitter)
            throws IOException {
        Path filePath = fileDto.getTmpFile();
        CsvDto<C> csvDto;
        List<C> csvList = new ArrayList<>();
        int rowCount = 0;
        sendEmitterProgressResponse(emitter, "validation");
        try (CsvAnnotationBeanReader<C> csvReader = new CsvAnnotationBeanReader<>(
                csvCommonService.getBeanMapping(job.getCsvClass()),
                FileUtil.newBufferedReader(filePath, Charset.forName(job.getEncode())),
                CsvPreference.STANDARD_PREFERENCE)) {
            if (job.hasHeader()) {
                csvReader.getHeader(true);
            }
            while (true) {
                try {
                    C csv = csvReader.read();
                    if (Objects.isNull(csv)) {
                        break;
                    }
                    csv.setRowNum(csvReader.getRowNumber());
                    csvList.add(csv);
                } catch (SuperCsvException e) {
                    // nop
                } finally {
                    rowCount++;
                }
            }
            sendEmitterProgressResponse(emitter, "extraValidationWithImage");
            List<String> errors = new ArrayList<>();
            List<String> extraValidationErrors = job.extraValidation(csvList, emitter, account);
            if (extraValidationErrors != null) {
                errors.addAll(extraValidationErrors);
            }
            extraValidationErrors =
                    job.extraValidation(csvList, fileDto.getTmpImages(), emitter, account);
            if (extraValidationErrors != null) {
                errors.addAll(extraValidationErrors);
            }
            if (csvReader.getErrorMessages() != null) {
                errors.addAll(csvReader.getErrorMessages());
            }
            if (errors.size() > 100) {
                errors = errors.subList(0, 100);
                errors.add(messageService.getMessage("csv.errors.over100Errors"));
            }
            csvDto = new CsvDto<>(filePath, csvList, errors, rowCount - 1);
            return csvDto;
        }
    }

    /**
     * SSEエミッターに処理成功のレスポンスを返却する
     *
     * @param emitter SSEエミッター
     * @param message メッセージ
     */
    public static void sendEmitterSuccessResponse(SseEmitter emitter, String message) {
        sendEmitterSuccessResponse(emitter, message, null);
    }

    /**
     * SSEエミッターに処理成功のレスポンスを返却する
     *
     * @param emitter      SSEエミッター
     * @param message      メッセージ
     * @param warnMessages 警告メッセージリスト
     */
    public static void sendEmitterSuccessResponse(SseEmitter emitter, String message,
                                                  List<String> warnMessages) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "SUCCESS");
        data.put("message", message);
        if (warnMessages != null && !warnMessages.isEmpty()) {
            data.put("errorMessages", warnMessages);
        }
        sendEmitterResponse(emitter, data);
    }

    /**
     * SSEエミッターに処理失敗のレスポンスを返却する
     *
     * @param emitter SSEエミッター
     * @param message メッセージ
     */
    public static void sendEmitterErrorResponse(SseEmitter emitter, String message) {
        sendEmitterErrorResponse(emitter, message, null);
    }

    /**
     * SSEエミッターに処理失敗のレスポンスを返却する
     *
     * @param emitter      SSEエミッター
     * @param message      メッセージ
     * @param warnMessages 警告メッセージリスト
     */
    public static void sendEmitterErrorResponse(SseEmitter emitter, String message,
                                                List<String> warnMessages) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "ERROR");
        data.put("message", message);
        if (warnMessages != null && !warnMessages.isEmpty()) {
            data.put("errorMessages", warnMessages);
        }
        sendEmitterResponse(emitter, data);
    }

    /**
     * SSEエミッターに途中経過のレスポンスを返却する<br>
     * ※タイムアウト防止用
     *
     * @param emitter SSEエミッター
     * @param message 状態メッセージ
     */
    public static void sendEmitterProgressResponse(SseEmitter emitter, String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("status", message);
        sendEmitterResponse(emitter, data);
    }

    /**
     * SSEエミッターに途中経過のレスポンスを返却する
     *
     * @param emitter  SSEエミッター
     * @param allCount 全件数
     * @param count    処理件数
     */
    public static void sendEmitterProgressResponse(SseEmitter emitter, int allCount, int count) {
        if (allCount < count) {
            allCount = count;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("allCount", allCount);
        data.put("count", count);
        sendEmitterResponse(emitter, data);
    }

    /**
     * SSEエミッターに途中経過のレスポンスを返却する<br>
     * ※処理件数が送信単位で割り切れる場合のみ送信する
     *
     * @param emitter  SSEエミッター
     * @param allCount 全件数
     * @param count    処理件数
     * @param unit     送信単位
     */
    public static void sendEmitterProgressResponse(SseEmitter emitter, int allCount, int count,
                                                   int unit) {
        if (unit < 1) {
            return;
        }
        if (count % unit != 0) {
            return;
        }
        sendEmitterProgressResponse(emitter, allCount, count);
    }

    /**
     * SSEエミッターに途中経過のレスポンスを返却する<br>
     * 全件数に応じてレスポンスの送信調整を行います。
     *
     * @param emitter  SSEエミッター
     * @param allCount 全件数
     * @param count    処理件数
     */
    public static void sendEmitterProgressResponseAuto(SseEmitter emitter, int allCount,
                                                       int count) {
        int unit = 1;
        if (allCount >= 100000) {
            unit = 1000;
        } else if (allCount >= 50000) {
            unit = 500;
        } else if (allCount >= 10000) {
            unit = 100;
        } else if (allCount >= 5000) {
            unit = 50;
        } else if (allCount >= 1000) {
            unit = 10;
        } else if (allCount >= 500) {
            unit = 5;
        }
        sendEmitterProgressResponse(emitter, allCount, count, unit);
    }

    /**
     * SSEエミッターにレスポンスを返却する
     *
     * @param emitter SSEエミッター
     * @param data    レスポンスデータ
     */
    public static void sendEmitterResponse(SseEmitter emitter, Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(data);
            emitter.send(json, MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}