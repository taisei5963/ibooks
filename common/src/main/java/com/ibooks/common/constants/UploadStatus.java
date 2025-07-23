package com.ibooks.common.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * アップロードステータス
 */
public enum UploadStatus implements MapValue {
    SUCCESS(0, "正常終了"),
    WARN(1, "一部異常終了"),
    ERROR(2, "全件異常終了"),
    EXECUTE(9, "取込処理中");

    /** 値 */
    private final int value;
    /** 説明 */
    private final String description;

    /**
     * コンストラクタ
     *
     * @param value       値
     * @param description 説明
     */
    UploadStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return String.valueOf(this.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 最終ステータスのリストを取得する
     *
     * @return 最終ステータスのリスト
     */
    public static List<String> getLastStatus() {
        List<String> list = new ArrayList<>();
        list.add(UploadStatus.SUCCESS.getValue());
        list.add(UploadStatus.WARN.getValue());
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.value + "," + super.toString();
    }

    /**
     * 引数の値の UploadStatus 列挙型を返却する
     *
     * @param value 値
     * @return UploadStatus 列挙型
     */
    public static UploadStatus getEnum(String value) {
        for (UploadStatus status : UploadStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
