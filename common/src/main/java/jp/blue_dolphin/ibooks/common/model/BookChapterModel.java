package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ブックチャプターモデル
 */
@Builder(toBuilder = true)
@Getter
public class BookChapterModel {
    /** ブックチャプターID */
    private Long bookChapterId;

    /** ブックID */
    private Long bookId;

    /** 章番号 */
    private Integer chapterNo;

    /** タイトル */
    private String title;

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
