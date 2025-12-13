package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.blue_dolphin.ibooks.common.constant.AvailableFlg;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * アクションロールモデル
 */
@Builder(toBuilder = true)
@Getter
public class ActionRoleModel {
    /** アクションロールID */
    private Long actionRoleId;

    /** アクションパス */
    private String path;

    /** 権限ID */
    public Long privilegeId;

    /** 利用可否フラグ */
    private AvailableFlg availableFlg;

    /** 備考 */
    private String note;

    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /** 作成者ID */
    private String createdId;

    /** バージョン */
    private Integer ver;
}
