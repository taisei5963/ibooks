package jp.blue_dolphin.ibooks.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * ブックモデル
 */
@Builder(toBuilder = true)
@Getter
public class BookModel {
    /** ダミーJANコード */
    public static final String DUMMY_JAN_CODE = "9999999999999";

    /** ブックID */
    private Long bookId;

    /** JANコード */
    private String janCode;

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

    /** 画像ファイル名 */
    private String picFileName;

    /** 全体評価 */
    private BigDecimal totalRating;

    /** カテゴリID1 */
    private Long categoryId1;

    /** カテゴリID2 */
    private Long categoryId2;

    /** カテゴリID3 */
    private Long categoryId3;

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

    /** カテゴリモデル1（表示用） */
    @Setter
    private SearchCategoryBookModel categoryModel1;

    /** カテゴリコード1（表示用） */
    @Setter
    private String categoryCode1;

    /** カテゴリ名1（表示用） */
    @Setter
    private String categoryName1;

    /** カテゴリモデル2（表示用） */
    @Setter
    private SearchCategoryBookModel categoryModel2;

    /** カテゴリコード2（表示用） */
    @Setter
    private String categoryCode2;

    /** カテゴリ名2（表示用） */
    @Setter
    private String categoryName2;

    /** カテゴリモデル3（表示用） */
    @Setter
    private SearchCategoryBookModel categoryModel3;

    /** カテゴリコード3（表示用） */
    @Setter
    private String categoryCode3;

    /** カテゴリ名3（表示用） */
    @Setter
    private String categoryName3;

    /** ブック画像URL（表示用） */
    @Setter
    private String picFileUrl;

    /**
     * リネーム用の新しいブック画像ファイル名を作成する
     *
     * @param bookId         ブックID
     * @param uploadFileName アップロードされた画像ファイル名
     * @param imgNo          画像番号
     * @return 新しいブック画像ファイル名
     */
    public static String createNewImageFileName(Long bookId, String uploadFileName, Integer imgNo) {
        String ext = uploadFileName.substring(uploadFileName.lastIndexOf(".")).toLowerCase();
        Random rand = new Random();
        int num = rand.nextInt(999999);
        String hash = Strings.crc32(uploadFileName + "-" + num);
        return "IMG" + bookId + "-" + imgNo + "-" + hash + ext;
    }
}
