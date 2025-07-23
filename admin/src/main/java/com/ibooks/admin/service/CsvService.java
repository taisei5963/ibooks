package com.ibooks.admin.service;

import com.ibooks.common.service.CsvCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * CSVサービス
 */
public class CsvService {
    /** ログ */
    private final static Logger log = LoggerFactory.getLogger(CsvService.class);

    /** CSV共通サービス */
    private CsvCommonService csvCommonService;

    /**
     * CSVファイルを出力する
     *
     * @param path   出力ファイルパス
     * @param list   CSVデータリスト
     * @param cls    CSVデータクラス
     * @param encode 文字エンコード
     * @throws IOException 書き込みができなかった場合にスローされる
     */
    public <Z> void createCsv(Path path, List<Z> list, Class<Z> cls, String encode) throws IOException {
        csvCommonService.createCsv(path, list, cls, encode);
    }

    /**
     * CSVファイルを出力する
     *
     * @param path   出力ファイルパス
     * @param list   CSVデータリスト
     * @param encode 文字エンコード
     * @throws IOException 書き込みができなかった場合にスローされる
     */
    public void createCsv(Path path, List<List<Object>> list, String encode) throws IOException {
        csvCommonService.createCsv(path, list, encode);
    }
}
