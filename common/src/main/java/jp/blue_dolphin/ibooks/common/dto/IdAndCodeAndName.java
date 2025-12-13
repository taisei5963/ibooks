package jp.blue_dolphin.ibooks.common.dto;

import jp.blue_dolphin.ibooks.common.util.Strings;
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
    @Column(name = "ID")
    private final Long id;

    /** コード */
    @Column(name = "CODE")
    private final String code;

    /** 名称 */
    @Column(name = "NAME")
    private final String name;

    /**
     * 引数のIDとコード、名称を返却する
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
     * ID の文字列を返却する
     * @return ID の文字列
     */
    public String getIdString() {
        return Strings.toString(id);
    }
}
