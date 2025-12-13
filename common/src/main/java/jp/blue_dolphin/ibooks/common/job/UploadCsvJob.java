package jp.blue_dolphin.ibooks.common.job;

import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.constant.UploadType;
import jp.blue_dolphin.ibooks.common.dto.Account;
import jp.blue_dolphin.ibooks.common.dto.CsvDto;
import jp.blue_dolphin.ibooks.common.dto.TempFileDto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.List;

/**
 * CSVファイルアップロードジョブ
 */
public interface UploadCsvJob<C> {
    /**
     * アップロードファイルのチェック（ファイル形式に関するチェック）を行う<br>
     * ※必要に応じてオーバーライドすること
     *
     * @param file アップロードファイル
     * @return チェック結果エラーコード（エラー発生時のみ返す）
     */
    default String validateUploadFile(MultipartFile file) {
        return null;
    }

    /**
     * アップロードされたファイルを一時ファイルに保存する<br>
     * ※必要に応じてオーバーライドすること
     *
     * @param file    アップロードファイル
     * @param account アカウント
     * @return 一時ファイルDTO
     */
    TempFileDto saveTempFile(MultipartFile file, Account account);

    /**
     * インポート処理<br>
     * 当該メソッド内でエラー、警告検知した場合はCsvDtoオブジェクトに設定する
     *
     * @param csvDto      CsvDto
     * @param tempFileDto 一時ファイルDTO
     * @param emitter     SSEエミッター
     * @param account     アカウント
     */
    void execImport(CsvDto<C> csvDto, TempFileDto tempFileDto, SseEmitter emitter, Account account);

    /**
     * カスタムバリエーション<br>
     * アノテーションによるバリデーション後に呼ばれる<br>
     * アノテーションによるバリデーションでエラーになった行は含まれない<br>
     * ※必要に応じてオーバライドすること<br>
     * ※処理に時間がかかる場合はエミッターに途中経過を送信してタイムアウトを防止すること
     *
     * @param csvList CSVリスト
     * @param emitter SSEエミッター
     * @param account アカウント
     * @return エラーリスト
     */
    default List<String> extraValidation(List<C> csvList, SseEmitter emitter, Account account) {
        return null;
    }

    /**
     * CSVインポート前処理
     * ※必要に応じてオーバライドすること
     *
     * @param csvDto      CsvDto
     * @param tempFileDto 一時ファイルDTO
     * @param emitter     SSEエミッター
     */
    default void beforeImport(CsvDto<C> csvDto, TempFileDto tempFileDto, SseEmitter emitter) {
    }

    /**
     * インポート後処理
     * ※必要に応じてオーバライドすること
     *
     * @param csvDto  CsvDto
     * @param emitter SSEエミッター
     */
    default void afterImport(CsvDto<C> csvDto, SseEmitter emitter) {
    }

    /**
     * カスタムバリエーション<br>
     * アノテーションによるバリデーション後に呼ばれる<br>
     * アノテーションによるバリデーションでエラーになった行は含まれない<br>
     * ※必要に応じてオーバライドすること<br>
     * ※処理に時間がかかる場合はエミッターに途中経過を送信してタイムアウトを防止すること
     *
     * @param csvList    CSVリスト
     * @param tempImages 一時画像ファイルリスト
     * @param emitter    SSEエミッター
     * @param account    アカウント
     * @return エラーリスト
     */
    default List<String> extraValidation(List<C> csvList, List<Path> tempImages, SseEmitter emitter,
                                         Account account) {
        return null;
    }

    /**
     * CSVクラスを取得する
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
     * ※必要に応じてオーバライドすること
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
     * true を返却するとCSVの登録前にリストをソートする<br>
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
