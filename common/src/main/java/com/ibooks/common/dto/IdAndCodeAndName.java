package com.ibooks.common.dto;

import com.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * IDとコードと名称を取得する汎用エンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
public class IdAndCodeAndName {
    /** ID */
    @Column(name = "id")
    public final Long id;

    /** コード */
    @Column(name = "code")
    public final String code;

    /** 名称 */
    @Column(name = "name")
    public final String name;

    /**
     * 引数のIDとコード、名称を取得する
     *
     * @param id   ID
     * @param code コード
     * @param name 名称
     * @return IDとコード、名称
     */
    public static IdAndCodeAndName of(Long id, String code, String name) {
        return new IdAndCodeAndName(id, code, name);
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
