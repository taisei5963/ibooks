package jp.blue_dolphin.ibooks.common.constant;

/**
 * 汎用正規表現定義
 */
public class SystemRegex {
    /** データ区分の追加と更新に対する正規表現 */
    public static final String ADD_UPDATE_REGEX = "^[AU]?$";
    /** JANコードに対する正規表現 */
    public static final String JAN_CODE_REGEX = "^(\\d{13})?$";
    /** ブック画像ファイル名に対する正規表現 */
    public static final String BOOK_IMG_REGEX = "^(.*\\.(jpg|JPG|jpeg|JPEG|png|PNG|登録済)?$)";
    /** カテゴリコードに対する正規表現 */
    public static final String CATEGORY_CODE_REGEX = "^[0-9A-Z]*$";
    /** コード値に対する正規表現 */
    public static final String CODE_REGEX = "^[0-9a-zA-Z_\\-]*$";
    /** パスワードに対する正規表現 */
    public static final String PASSWORD_REGEX = "^[0-9a-zA-Z]*$";
    /** 一時画像ファイル拡張子に対する正規表現 */
    public static final String TEMP_IMG_FILE_EXTENSION = "^.*\\.(jpg|jpeg|png|csv)$";
    /** ファイル種別に対する正規表現 */
    public static final String FILE_EXTENSION = "^.*\\.(zip|csv)$";
}
