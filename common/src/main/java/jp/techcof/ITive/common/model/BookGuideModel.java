package jp.techcof.ITive.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ブックガイドモデル
 */
@Builder(toBuilder = true)
@Getter
public class BookGuideModel {
    /** ブックガイドID */
    private Long bookGuideId;

    /** ブックID */
    private Long bookId;

    /** おすすめ対象者 */
    private String recommendFor;

    /** おすすめタイミング */
    private String recommendedTiming;

    /** おすすめポイント */
    private String recommendedPoints;

    /** 次のおすすめ */
    private String nextRecommended;

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
