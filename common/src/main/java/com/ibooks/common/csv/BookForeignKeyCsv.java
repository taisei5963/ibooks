package com.ibooks.common.csv;

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
     * カテゴリID1を取得する
     *
     * @return カテゴリID1
     */
    Long getCategoryId1();

    /**
     * カテゴリID2を取得する
     *
     * @return カテゴリID2
     */
    Long getCategoryId2();

    /**
     * カテゴリID3を取得する
     *
     * @return カテゴリID3
     */
    Long getCategoryId3();

    /**
     * カテゴリコード1を設定する
     *
     * @param categoryCode カテゴリコード
     */
    void setCategoryCode1(String categoryCode);

    /**
     * カテゴリコード2を設定する
     *
     * @param categoryCode カテゴリコード
     */
    void setCategoryCode2(String categoryCode);

    /**
     * カテゴリコード3を設定する
     *
     * @param categoryCode カテゴリコード
     */
    void setCategoryCode3(String categoryCode);

}
