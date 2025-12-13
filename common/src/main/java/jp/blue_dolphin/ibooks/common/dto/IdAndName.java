package jp.blue_dolphin.ibooks.common.dto;

import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.Builder;
import lombok.Getter;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * IDと名称を取得する汎用エンティティ
 */
@Getter
@Builder(toBuilder = true)
@Entity(immutable = true)
public class IdAndName {
    /** ID */
    @Column(name = "ID")
    private final Long id;

    /** 名称 */
    @Column(name = "NAME")
    private final String name;

    /**
     * ID の文字列を返却する
     * @return ID の文字列
     */
    public String getIdString() {
        return Strings.toString(id);
    }
}
