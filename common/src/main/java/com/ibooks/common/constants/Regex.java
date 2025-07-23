package com.ibooks.common.constants;

/**
 * 正規表現
 */
public class Regex {
    /** データ区分に対する正規表現の規定 */
    public static final String DATA_TYPE_REGEX = "^[AU]?$";
    /** ブックコードに対する正規表現の規定 */
    public static final String BOOK_CODE_REGEX = "^(\\d{13})?$";
    /** ブック画像ファイル名に対する正規表現の規定 */
    public static final String BOOK_IMG_REGEX = "^(.*\\.(jpg|JPG|jpeg|JPEG|登録済)?$";

    /** 半角数字に対する正規表現の規定 */
    public static final String NUMBER_REGEX = "^[0-9]+$";
    /** 半角数字（4桁固定)に対する正規表現の規定 */
    public static final String NUMBER_4_REGEX = "^([0-9]{4})?$";
    /** コード値に対する正規表現の規定 */
    public static final String CODE_REGEX = "^[0-9a-zA-Z_\\-]*$";
    /** パスワードに対する正規表現の規定 */
    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]*$";
    /** ファイル拡張子に対する正規表現の規定 */
    public static final String CSV_FILE_EXTENSION_REGEX = "^.*\\.(csv)$";
}
