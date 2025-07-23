package com.ibooks.common.dto;

import com.ibooks.common.util.Strings;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * IDと名称を取得する汎用エンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
public class IdAndName {
    /** ID */
    @Column(name = "id")
    public final Long id;

    /** 名称 */
    @Column(name = "name")
    public final String name;

    /**
     * コンストラクタ
     *
     * @param id   ID
     * @param name 名称
     */
    public IdAndName(Long id, String name) {
        this.id = id;
        this.name = name;
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
