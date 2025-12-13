package jp.blue_dolphin.ibooks.common.dto;

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
    @Column(name = "CODE")
    private final String code;

    /** 名称 */
    @Column(name = "NAME")
    private final String name;
}
