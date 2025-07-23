package com.ibooks.common.dto;

import com.ibooks.common.util.Strings;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * IDとコードを取得する汎用エンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
public class IdAndCode {
    /** ID */
    @Column(name = "id")
    public final Long id;

    /** コード */
    @Column(name = "code")
    public final String code;

    /**
     * コンストラクタ
     *
     * @param id   ID
     * @param code コード
     */
    public IdAndCode(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    /**
     * IDの文字列を返却する
     *
     * @return IDの文字列
     */
    public String getIdString() {
        return Strings.toString(id);
    }
}
