package com.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibooks.common.util.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * ブックモデル
 */
@Getter
@Builder(toBuilder = true)
public class BookModel {
    /** ブックID */
    private Long bookId;

    /** ブックコード */
    private String bookCode;

    /** タイトル */
    private String title;

    /** サブタイトル */
    private String subTitle;

    /** 著者1 */
    private String author1;

    /** 著者2 */
    private String author2;

    /** 翻訳者 */
    private String translator;

    /** 出版社 */
    private String publisher;

    /** 画像ファイル */
    private String picFileName;

    /** 全体評価 */
    private Double totalRating;

    /** カテゴリID1 */
    private Long categoryId1;

    /** カテゴリID2 */
    private Long categoryId2;

    /** カテゴリID3 */
    private Long categoryId3;

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

    /** カテゴリ1名（表示用） */
    @Setter
    private String categoryName1;

    /** カテゴリ2名（表示用） */
    @Setter
    private String categoryName2;

    /** カテゴリ3名（表示用） */
    @Setter
    private String categoryName3;

    /** カテゴリ1モデル（表示用） */
    @Setter
    private CategoryModel categoryModel1;

    /** カテゴリ2モデル（表示用） */
    @Setter
    private CategoryModel categoryModel2;

    /** カテゴリ3モデル（表示用） */
    @Setter
    private CategoryModel categoryModel3;

    /** ブック画像URL */
    @Setter
    private String imgFileUrl;

    /**
     * リネーム用の新しいブック画像ファイル名を作成する
     * @param bookId ブックID
     * @param orgFileName アップロードされた画像ファイル名
     * @param imgNo 画像番号
     * @return 新しいブック画像ファイル名
     */
    public static String generateNewImgFile(Long bookId, String orgFileName, Integer imgNo) {
        String ext = orgFileName.substring(orgFileName.lastIndexOf('.')).toLowerCase();
        Random rand = new Random();
        int num = rand.nextInt(9999999);
        String hash = Strings.crc32(orgFileName + "-" + num);
        return "IMG" + bookId + "-" + imgNo + "-" + hash + ext;
    }
}
