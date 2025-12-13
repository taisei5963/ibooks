package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.blue_dolphin.ibooks.common.constant.MaintenanceStatus;
import jp.blue_dolphin.ibooks.common.constant.MaintenanceType;
import jp.blue_dolphin.ibooks.common.constant.SiteType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * メンテナンスモデル
 */
@Builder(toBuilder = true)
@Getter
public class MaintenanceModel {
    /** メンテナンスID */
    private Long maintenanceId;

    /** 名称 */
    private String name;

    /** メンテナンスタイプ */
    private MaintenanceType type;

    /** サイト種別 */
    private SiteType siteType;

    /** ステータス */
    private MaintenanceStatus status;

    /** 適用開始日時 */
    private LocalDateTime effectiveFrom;

    /** 適用終了日時 */
    private LocalDateTime effectiveTo;

    /** メッセージ */
    private String message;

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
