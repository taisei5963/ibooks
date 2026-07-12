package jp.techcof.ITive.common.dto;

import lombok.Data;

/**
 * 次に読むべき書籍DTO
 */
@Data
public class NextRecommendedBookDto {
    /** タイトル */
    private String title;
    /** 出版社 */
    private String publisher;
    /** 画像保持有無 */
    private boolean hasImg;
    /** 画像URL */
    private String imgUrl;
}
