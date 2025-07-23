package com.ibooks.common.service;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.builder.BeanMapping;
import com.github.mygreen.supercsv.builder.BeanMappingFactory;
import com.github.mygreen.supercsv.builder.SpringBeanFactory;
import com.github.mygreen.supercsv.exception.SuperCsvBindingException;
import com.github.mygreen.supercsv.io.CsvAnnotationBeanWriter;
import com.ibooks.common.dto.Account;
import com.ibooks.common.dto.PageDto;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.job.DownloadCsvJob;
import com.ibooks.common.request.SearchForm;
import com.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV 共通サービス
 */
@AllArgsConstructor
@Service
public class CsvCommonService {
    /** ログ */
    private static final Logger log = LoggerFactory.getLogger(CsvCommonService.class);

    /** SuperCsv 用ファクトリー */
    private SpringBeanFactory factory;

    /**
     * Bean マッピングを返却する
     *
     * @param cls CSVクラス
     * @return Beanマッピング
     */
    public <Z> BeanMapping<Z> getBeanMapping(Class<Z> cls) {
        BeanMappingFactory beanMappingFactory = new BeanMappingFactory();
        beanMappingFactory.getConfiguration().setBeanFactory(factory);
        return beanMappingFactory.create(cls);
    }

    /**
     * CSVライターを取得する<br>
     * ※自動的にはクローズされないため、必ず返却するライターのクローズ処理を行ってください
     *
     * @param path   出力ファイルパス
     * @param cls    CSVデータクラス
     * @param encode 文字エンコード
     * @return CSVライター
     */
    public <Z> CsvAnnotationBeanWriter<Z> getCsvWriter(Path path, Class<Z> cls, String encode)
            throws IOException {
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName(encode));
        CsvAnnotationBeanWriter<Z> csvWriter =
                new CsvAnnotationBeanWriter<>(getBeanMapping(cls), writer,
                        CsvPreference.STANDARD_PREFERENCE);
        writeBom(writer, encode);
        CsvBean annotation = AnnotationUtils.findAnnotation(cls, CsvBean.class);
        if (annotation != null && annotation.header()) {
            csvWriter.writeHeader();
        }
        return csvWriter;
    }

    /**
     * 引数のライターに BOM を書き込む<br>
     * 引数のエンコードが utf8 以外の場合は何もしない
     *1
     * @param writer ライター
     * @param encode 文字エンコード
     */
    public static void writeBom(Writer writer, String encode) throws IOException {
        if (!Strings.isEmpty(encode) && encode.equalsIgnoreCase("utf8")) {
            writer.write("\uFEFF");
        }
    }

    /**
     * CSVデータをCSVライターに書き込む
     *
     * @param csvWriter CSVライター
     * @param list      CSVデータリスト
     */
    public <Z> void writeCsv(CsvAnnotationBeanWriter<Z> csvWriter, List<Z> list)
            throws IOException {
        try {
            for (Z row : list) {
                csvWriter.write(row);
            }
            csvWriter.flush();
        } catch (SuperCsvBindingException e) {
            log.error("CSVデータへの変換に失敗しました。", e);
            csvWriter.getErrorMessages().forEach(log::error);
            throw e;
        }
    }

    /**
     * CSVファイルを出力する
     *
     * @param path   出力ファイルパス
     * @param list   CSVデータリスト
     * @param cls    CSVデータクラス
     * @param encode 文字エンコード
     */
    public <Z> void createCsv(Path path, List<Z> list, Class<Z> cls, String encode)
            throws IOException {
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName(encode));
            CsvAnnotationBeanWriter<Z> csvWriter =
                    new CsvAnnotationBeanWriter<>(getBeanMapping(cls), writer,
                            CsvPreference.STANDARD_PREFERENCE);

            try {
                writeBom(writer, encode);
                csvWriter.writeAll(list);
            } catch (SuperCsvBindingException e) {
                log.error("CSVデータへの変換に失敗しました。", e);
                csvWriter.getErrorMessages().forEach(log::error);
                throw e;
            }
        } finally {
        }
    }

    /**
     * CSVファイルを作成する<br>
     *
     * @param job      CSVファイルダウンロードジョブ
     * @param form     検索フォーム
     * @param account  アカウント
     * @param emitter  SSEエミッター
     * @param key      ファイル照合キー
     * @param tempPath 一時ファイルパス
     */
    @Transactional
    public <T> void createFile(DownloadCsvJob<T> job, SearchForm form, Account account,
                               SseEmitter emitter, String key, Path tempPath) {
        try (CsvAnnotationBeanWriter<T> csvWriter = getCsvWriter(tempPath, job.getCsvClas(),
                job.getEncode())) {
            Pageable pageable = Pageable.ofSize(1000).first();
            while (writeCsv(csvWriter, pageable, job, form, emitter, account)) {
                pageable = pageable.next();
            }
            job.afterExport(tempPath, account);
            DownloadCsvService.sendEmitterSuccessResponse(emitter, key);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            DownloadCsvService.sendEmitterErrorResponse(emitter);
        } finally {
            emitter.complete();
        }
    }

    /**
     * CSVライターにCSVデータを取得して書き込む
     *
     * @param csvWriter CSVライター
     * @param pageable  ページャブル
     * @param job       CSVファイルダウンロードジョブ
     * @param form      検索フォーム
     * @param emitter   SSEエミッター（省略可）
     * @param account   アカウント
     * @return {@code true} 未取得のデータあり
     */
    public <T> boolean writeCsv(CsvAnnotationBeanWriter<T> csvWriter, Pageable pageable,
                                DownloadCsvJob<T> job, SearchForm form, SseEmitter emitter,
                                Account account)
            throws IOException {
        SearchResult<T> result = job.exportCsvList(form, pageable, account);
        int maxPage = PageDto.maxPageNum(pageable, result.getCount());
        if (!result.isEmpty()) {
            List<T> csvList = result.getList();
            writeCsv(csvWriter, csvList);
            job.afterExportRow(form, csvList, account);
            if (emitter != null) {
                UploadCsvService.sendEmitterProgressResponse(emitter, (int) result.getCount(),
                        (int) pageable.getOffset() + csvList.size());
            }
        }
        return pageable.getPageNumber() < maxPage;
    }

    /**
     * CSVファイルを出力する
     *
     * @param path   出力ファイルパス
     * @param list   CSVデータリスト
     * @param encode 文字エンコード
     */
    public void createCsv(Path path, List<List<Object>> list, String encode) throws IOException {
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        try (
                BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName(encode));
                CsvListWriter listWriter = new CsvListWriter(writer,
                        CsvPreference.STANDARD_PREFERENCE)) {
            writeBom(writer, encode);
            for (List<Object> row : list) {
                listWriter.write(row);
            }
        }
    }
}
