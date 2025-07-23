package com.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * アップロード履歴詳細モデル
 */
@Getter
@Builder(toBuilder = true)
public class UploadHrDetailModel {
    /** アップロード履歴詳細ID */
    private Long uploadHrDetailId;
    /** アップロード履歴ID */
    private Long uploadHrId;
    /** ログレベル */
    private String loglevel;
    /** 内容 */
    private String content;
    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
}
