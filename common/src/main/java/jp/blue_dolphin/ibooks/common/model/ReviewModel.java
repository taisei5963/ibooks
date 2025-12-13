package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.blue_dolphin.ibooks.common.constant.Flag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * レビューモデル
 */
@Builder(toBuilder = true)
@Getter
public class ReviewModel {
    /** レビューID */
    public Long reviewId;

    /** ブックID */
    public Long bookId;

    /** レビュワーID */
    public Long reviewerId;

    /** 評価値 */
    public Integer rating;

    /** コメント */
    public String comment;

    /** レビュー表示フラグ */
    public Flag reviewViewFlag;

    /** レビュー日 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate reviewDate;

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
