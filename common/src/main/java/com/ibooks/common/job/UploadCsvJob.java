package com.ibooks.common.job;

import com.ibooks.common.constants.SiteType;
import com.ibooks.common.constants.UploadType;
import com.ibooks.common.dto.Account;
import com.ibooks.common.dto.CsvDto;
import com.ibooks.common.dto.TempFileDto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.List;

/**
 * CSVファイルアップロードジョブ
 */
public interface UploadCsvJob<C> {

    /**
     * アップロードファイルのチェック（ファイル形式に関するチェック）を行う。<br>
     * ※必要に応じてオーバーライドしてください。
     *
     * @param file アップロードファイル
     * @return チェック結果のエラーコード（エラー発生時のみ返す）
     */
    default String validateUploadFile(MultipartFile file) {
        return null;
    }

    /**
     * アップロードされたファイルを一時ファイルに保存する<br>
     * ZIPファイルでのアップロードを共有する機能は利用側で戻り値をパラメータとして TempFileDto インスタンス生成すること
     *
     * @param file    アップロードされたファイル
     * @param account アカウント
     * @return 一時ファイルDTO
     */
    TempFileDto saveTemFile(MultipartFile file, Account account);

    /**
     * インポート処理<br>
     * 当該メソッド内でエラー、警告検知した場合は CsvDto オブジェクトに設定する
     *
     * @param csvDto      CsvDto
     * @param tempFileDto 一時ファイルDto
     * @param emitter     SSEエミッター
     * @param account     アカウント
     */
    void execImport(CsvDto<C> csvDto, TempFileDto tempFileDto, SseEmitter emitter, Account account);

    /**
     * カスタムバリデーション<br>
     * アノテーションによるバリデーションチェック後に呼ばれる<br>
     * アノテーションによるバリデーションでエラーになった行は含まれない<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @param csvList CSVリスト
     * @param account アカウント
     * @return エラーリスト
     */
    default List<String> extraValidation(List<C> csvList, Account account) {
        return null;
    }

    /**
     * CSV インポート前処理<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @param csvDto      CsvDto
     * @param tempFileDto 一時ファイルDTO
     * @param emitter     SSEエミッター
     */
    default void beforeImport(CsvDto<C> csvDto, TempFileDto tempFileDto, SseEmitter emitter) {
    }

    /**
     * CSV インポート後処理<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @param csvDto  CsvDto
     * @param emitter SSEエミッター
     */
    default void afterImport(CsvDto<C> csvDto, SseEmitter emitter) {
    }

    /**
     * カスタムバリデーション<br>
     * アノテーションによるバリデーション後に呼ばれる<br>
     * アノテーションによるバリデーションでエラーになった行は含まれない<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @param csvList    CSVリスト
     * @param tempImages 一時画像ファイルリスト
     * @param account    アカウント
     * @return エラーリスト
     */
    default List<String> extraValidation(List<C> csvList, List<Path> tempImages, Account account) {
        return null;
    }

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

    /**
     * CSVファイルにヘッダーが含まれるかどうか<br>
     * ※必要に応じてオーバーライドしてください
     *
     * @return {@code true} 含まれる
     */
    default boolean hasHeader() {
        return true;
    }

    /**
     * アップロード種別を取得する
     *
     * @return アップロード種別
     */
    UploadType getUploadType();

    /**
     * アップロードプロセス名を取得する
     *
     * @return アップロードプロセス名
     */
    String getUploadProcessName();

    /**
     * true を返すとCSVの登録前にリストをソートする<br>
     * 独自にソートする場合は false を返却する
     *
     * @return {@code true} ソートする
     */
    default boolean enableSort() {
        return true;
    }

    /**
     * サイト種別を取得する
     *
     * @return サイト種別
     */
    SiteType getSiteType();
}
