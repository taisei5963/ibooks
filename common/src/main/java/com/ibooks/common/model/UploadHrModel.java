package com.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibooks.common.constants.SiteType;
import com.ibooks.common.constants.UploadStatus;
import com.ibooks.common.constants.UploadType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * アップロード履歴モデル
 */
@Getter
@Builder(toBuilder = true)
public class UploadHrModel {
    /** アップロード履歴ID */
    private Long uploadHrId;
    /** アップロード種別 */
    private UploadType uploadType;
    /** アップロードステータス */
    private UploadStatus uploadStatus;
    /** ファイルパス */
    private String filePath;
    /** ファイルサイズ */
    private Integer fileSize;
    /** 対象件数 */
    private Integer rowCount;
    /** 取込対象件数 */
    private Integer importCount;
    /** サイト種別 */
    private SiteType siteType;
    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createDate;
    /** 更新日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateDate;
    /** 作成者ID */
    private String createId;
    /** バージョン */
    private Integer ver;
}
