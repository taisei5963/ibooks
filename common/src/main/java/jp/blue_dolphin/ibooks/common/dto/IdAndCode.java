package jp.blue_dolphin.ibooks.common.dto;

import jp.blue_dolphin.ibooks.common.util.Strings;
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
    @Column(name = "ID")
    private final Long id;

    /** コード */
    @Column(name = "CODE")
    private final String code;

    /**
     * ID の文字列を返却する
     * @return ID の文字列
     */
    public String getIdString() {
        return Strings.toString(id);
    }
}
