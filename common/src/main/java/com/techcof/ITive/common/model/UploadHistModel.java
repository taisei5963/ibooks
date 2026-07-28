package com.techcof.ITive.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techcof.ITive.common.constant.SiteType;
import com.techcof.ITive.common.constant.UploadStatus;
import com.techcof.ITive.common.constant.UploadType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * アップロード履歴モデル
 */
@Builder(toBuilder = true)
@Getter
public class UploadHistModel {
    /** アップロード履歴ID */
    private Long uploadHistId;
    /** アップロード種別 */
    private UploadType uploadType;
    /** 処理ステータス */
    private UploadStatus uploadStatus;
    /** ファイルパス */
    private String filePath;
    /** ファイルサイズ */
    private Integer fileSize;
    /** 対象件数 */
    private Integer rowCount;
    /** 取込件数 */
    private Integer importCount;
    /** サイト種別 */
    private SiteType siteType;
    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;
    /** 削除日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime deletedAt;
    /** 作成者ID */
    private String createdId;
    /** バージョン */
    private Integer ver;
}
