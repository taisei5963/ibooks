package jp.blue_dolphin.ibooks.common.constant;

/**
 * フレームワークがスローするシステム例外エラーコード
 */
public enum ErrorCode {
    ILLEGAL_ARGUMENT("E101", "パラメータが不正です。"),
    UNSUPPORTED_OPERATION("E102", "サポートしていない操作です。"),
    IO_ERROR("E103", "入出力エラーが発生しました。"),
    PARSE_ERROR("E104","パースエラーが発生しました。"),
    FILE_NOT_FOUND("E105", "指定されたファイルが存在しません。"),
    DB_ACCESS_ERROR("E106","データベースへのアクセスでエラーが発生しました。"),
    ALREADY_DELETED("E107", "すでに削除済みです。"),
    ALREADY_UPDATE("E108", "すでに更新済みです。"),
    CRYPT_ERROR("E109", "暗号化モジュールでエラーが発生しました。"),
    ENCRYPT_ERROR("E110","暗号化処理でエラーが発生しました。"),
    DECRYPT_ERROR("E111","復号化処理でエラーが発生しました。"),
    INVALID_TOKEN("E112","不正なトークンが送信されました。"),
    SYSTEM_ERROR("E999", "システムエラーが発生しました。");

    private final String errorCode;
    private final String errorDescription;

    /**
     * コンストラクタ
     *
     * @param errorCode        例外の種類を表すエラーコード
     * @param errorDescription 例外の詳細を表すメッセージ
     */
    ErrorCode(final String errorCode, final String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    /**
     * getStatusCode()で取得できる値との比較を行う<br>
     * ・ILLEGAL_ARGUMENT.equivalent(errorCode);<br>
     * ・ILLEGAL_ARGUMENT.getStatusCode().equals(errorCode);<br>
     * は同じ意味を表している<br>
     *
     * @param errorCode 例外の種類を表すエラーコード
     * @return {@code true} 同じ
     */
    public boolean equivalent(final String errorCode) {
        return this.errorCode.equals(errorCode);
    }

    /**
     * 引数のエラーコードと一致するエラーコードを返却する
     *
     * @param errorCode 例外の種類を表すエラーコード
     * @return エラーコード
     */
    public static ErrorCode getInstance(final String errorCode) {
        ErrorCode result = null;
        for (ErrorCode ec : ErrorCode.values()) {
            if (ec.equivalent(errorCode)) {
                result = ec;
                break;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s : error code[%s] errorDescription[%s]", this.name(),
                this.errorCode, this.errorDescription);
    }
}
