package jp.blue_dolphin.ibooks.common.constant;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

/**
 * アカウントステータス
 */
@Getter
public enum AccountStatus implements MapValue {
    ACTIVE("1", "アクティブ"),
    WITHDRAWN("2", "退会済み"),
    SUSPENSION("3", "停止");

    private final String value;
    private final String description;

    AccountStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 引数の値のアカウントステータスを取得する
     *
     * @param value 値
     * @return アカウントステータス
     */
    public static AccountStatus getEnum(String value) {
        for (AccountStatus e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
