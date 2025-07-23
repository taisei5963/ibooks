package com.ibooks.common.dto;

import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * コードと名称を取得する汎用エンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
public class CodeAndName {
    /** コード */
    @Column(name = "code")
    public final String code;

    /** 名称 */
    @Column(name = "name")
    public final String name;

    /**
     * コンストラクタ
     *
     * @param code コード
     * @param name 名称
     */
    public CodeAndName(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
