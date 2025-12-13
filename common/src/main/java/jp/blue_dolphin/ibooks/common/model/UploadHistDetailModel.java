package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * アップロード履歴詳細モデル
 */
@Builder(toBuilder = true)
@Getter
public class UploadHistDetailModel {
    /** アップロード履歴詳細ID */
    private Long uploadHistDetailId;
    /** アップロード履歴ID */
    private Long uploadHistId;
    /** ログレベル */
    private String logLevel;
    /** 内容 */
    private String content;
    /** 作成日時 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
}
