package com.ibooks.common.job;

import com.ibooks.common.dto.Account;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.request.SearchForm;
import org.springframework.data.domain.Pageable;

import java.nio.file.Path;
import java.util.List;

/**
 * CSVファイルダウンロードジョブ
 */
public interface DownloadCsvJob<C> {
    /** 出力ファイル名 */
    public final static String FILE_NAME = "download_%s_%s_%s.csv";

    /**
     * 出力対象のの検索結果を返却する
     *
     * @param searchForm 検索フォーム
     * @param pageable   ページャブル
     * @param account    アカウント
     * @return 出力対象の検索結果
     */
    SearchResult<C> exportCsvList(SearchForm searchForm, Pageable pageable, Account account);

    /**
     * CSVエクスポート後処理<br>
     * すべての行を書き込んだ後で呼び出される<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @param searchForm 検索フォーム
     * @param csvList    CSVデータリスト
     * @param account    アカウント
     */
    default void afterExportRow(SearchForm searchForm, List<C> csvList, Account account) {
    }

    /**
     * CSVエクスポート後処理<br>
     * すべての行を書き込んだ後で呼び出される<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @param exportFile 出力ファイルパス
     * @param account    アカウント
     */
    default void afterExport(Path exportFile, Account account) {
    }

    /**
     * 出力するディレクトリパスを取得する
     *
     * @return 出力するディレクトリパス
     */
    String exportDir();

    /**
     * ファイルの識別子を取得する<br>
     * 出力するファイルのサブディレクトリとプレフィックスに使用される
     *
     * @return ファイルの識別子
     */
    String exportFileIdentifier();

    /**
     * CSVクラスを取得する
     *
     * @return CSVクラス
     */
    Class<C> getCsvClas();

    /**
     * CSVファイルの文字エンコードを取得する
     *
     * @return 文字エンコード
     */
    String getEncode();
}
