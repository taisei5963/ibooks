package jp.blue_dolphin.ibooks.common.csv;

/**
 * ブック（カテゴリ）
 */
public interface BookForeignKeyCsv {
    /**
     * ブックIDを取得する
     *
     * @return ブックID
     */
    Long getBookId();

    /**
     * カテゴリID１を取得する
     *
     * @return カテゴリID１
     */
    Long getCategoryId1();

    /**
     * カテゴリID２を取得する
     *
     * @return カテゴリID２
     */
    Long getCategoryId2();

    /**
     * カテゴリID３を取得する
     *
     * @return カテゴリID３
     */
    Long getCategoryId3();

    /**
     * カテゴリコード１を設定する
     *
     * @param categoryCode カテゴリコード
     */
    void setCategoryCode1(String categoryCode);

    /**
     * カテゴリコード２を設定する
     *
     * @param categoryCode カテゴリコード
     */
    void setCategoryCode2(String categoryCode);

    /**
     * カテゴリコード３を設定する
     *
     * @param categoryCode カテゴリコード
     */
    void setCategoryCode3(String categoryCode);
}
