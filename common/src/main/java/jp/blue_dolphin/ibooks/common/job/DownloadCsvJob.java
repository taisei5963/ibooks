package jp.blue_dolphin.ibooks.common.job;

import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.request.SearchForm;
import org.springframework.data.domain.Pageable;

import java.nio.file.Path;
import java.util.List;

/**
 * CSVファイルダウンロードジョブ
 */
public interface DownloadCsvJob<C> {
    /** 出力ファイル名 */
    public static final String FILE_NAME = "download_%s_%s_%s.csv";

    /**
     * 出力対象の検索結果を返却する
     *
     * @param searchForm 検索フォーム
     * @param pageable   ページャブル
     * @param account    アカウント
     * @return 出力対象の検索結果
     */
    SearchResult<C> exportCsvList(SearchForm searchForm, Pageable pageable, Account account);

    /**
     * CSVエクスポート後処理
     *
     * @param searchForm 検索フォーム
     * @param csvList    CSVデータリスト
     * @param account    アカウント
     */
    default void afterExportRow(SearchForm searchForm, List<C> csvList, Account account) {
    }

    /**
     * CSVエクスポート後処理
     *
     * @param exportFile 出力ファイルパス
     * @param account    アカウント
     */
    default void afterExportRow(Path exportFile, Account account) {
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
     * CSVクラスを返却する
     *
     * @return CSVクラス
     */
    Class<C> getCsvClass();

    /**
     * CSVファイルの文字エンコードを取得する
     *
     * @return 文字エンコード
     */
    String getEncode();
}
