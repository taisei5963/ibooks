package com.ibooks.common.csv;

/**
 * CSV行インタフェース
 */
public interface CsvRow {
    /**
     * デッドロック対策でCSV登録直前のソートに使用する値を取得する。<br>
     * エンティティにユニークなコードがある場合はコードを返却する。<br>
     * 複合でユニークになる場合は外部キーのIDセパレーター等で連携して返却する。<br>
     * ジョブでソートを無効にしている場合は使用されない。
     *
     * @return ソートに使用する値
     */
    String getSortValue();

    /**
     * 行番号を取得する
     *
     * @return 行番号
     */
    Integer getRowNum();

    /**
     * 行番号を設定する
     * @param rowNum 行番号
     */
    void setRowNum(Integer rowNum);
}
