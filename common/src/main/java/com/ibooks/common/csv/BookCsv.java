package com.ibooks.common.csv;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.annotation.constraint.CsvPattern;
import com.github.mygreen.supercsv.annotation.constraint.CsvRequire;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.ibooks.common.constants.CsvDataType;
import com.ibooks.common.constants.Regex;
import com.ibooks.common.model.BookModel;
import com.ibooks.common.util.Strings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ブックCSV
 */
@CsvBean(header = true)
@Setter
@Getter
public class BookCsv implements CsvRow, BookForeignKeyCsv {
    /** 登録済の画像ファイルカラムの出力値 */
    public static final String IMAGE_REGISTERED = "登録済";
    /** ダミーブックコード */
    private static final String DUMMY_BOOK_CODE = "0000000000000";

    @CsvColumn(label = "データ区分", number = 1)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvPattern(regex = Regex.DATA_TYPE_REGEX, message = "{csv.datatype.addUpdate.pattern", cases = BuildCase.Read)
    private String csvDataType;

    @CsvColumn(label = "ブックコード", number = 2)
    @CsvPattern(regex = Regex.BOOK_CODE_REGEX, message = "{csv.book_code.pattern}", cases = BuildCase.Read)
    private String bookCode;

    @CsvColumn(label = "タイトル", number = 3)
    private String title;

    @CsvColumn(label = "サブタイトル", number = 4)
    private String subTitle;

    @CsvColumn(label = "著者1", number = 5)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    private String author1;

    @CsvColumn(label = "著者2", number = 6)
    private String author2;

    @CsvColumn(label = "翻訳者", number = 7)
    private String translator;

    @CsvColumn(label = "出版社", number = 8)
    private String publisher;

    @CsvColumn(label = "ブック画像", number = 9)
    @CsvPattern(regex = Regex.BOOK_IMG_REGEX, message = "{csv.bookImage.pattern", cases = BuildCase.Read)
    private String picFileName;

    @CsvColumn(label = "全体評価", number = 10)
    private Double totalRating;

    @CsvColumn(label = "カテゴリコード1", number = 11)
    @CsvPattern(regex = Regex.NUMBER_4_REGEX, message = "{csv.number4.pattern}", cases = BuildCase.Read)
    private String categoryCode1;

    @CsvColumn(label = "カテゴリコード2", number = 12)
    @CsvPattern(regex = Regex.NUMBER_4_REGEX, message = "{csv.number4.pattern}", cases = BuildCase.Read)
    private String categoryCode2;

    @CsvColumn(label = "カテゴリコード3", number = 13)
    @CsvPattern(regex = Regex.NUMBER_4_REGEX, message = "{csv.number4.pattern}", cases = BuildCase.Read)
    private String categoryCode3;

    @CsvColumn(label = "作成日時", number = 14)
    private String createDate;

    @CsvColumn(label = "更新日時", number = 15)
    private String updateDate;

    @CsvColumn(label = "作成者ID", number = 16)
    private String createId;

    /** 行数 */
    private Integer rowNum;

    /** ブックID（キャッシュ用） */
    private Long bookId;
    /** カテゴリID1（キャッシュ用） */
    private Long categoryId1;
    /** カテゴリID2（キャッシュ用） */
    private Long categoryId2;
    /** カテゴリID3（キャッシュ用） */
    private Long categoryId3;
    /** ブックモデル（キャッシュ用） */
    private BookModel bookModel;

    /**
     * カテゴリIDリストを取得する
     *
     * @return カテゴリIDリスト
     */
    public List<Long> getCategoryIds() {
        List<Long> categoryIdList = new ArrayList<>();
        if (!Objects.isNull(categoryId1)) {
            categoryIdList.add(categoryId1);
        }
        if (!Objects.isNull(categoryId2) && !categoryIdList.contains(categoryId2)) {
            categoryIdList.add(categoryId2);
        }
        if (!Objects.isNull(categoryId3) && !categoryIdList.contains(categoryId3)) {
            categoryIdList.add(categoryId3);
        }
        return categoryIdList;
    }

    /**
     * ブックCSVをブックモデルに変換する<br>
     * ※画像ファイルはコピー処理時にリネームするため、モデルには CSV の値を設定しない
     * @return ブックモデル
     */
    public BookModel convertModel() {
        CsvDataType dataType = CsvDataType.getEnum(csvDataType);
        if (dataType == CsvDataType.ADD) {
            return BookModel.builder()
                    .bookCode(getBookCode())
                    .title(getTitle())
                    .subTitle(getSubTitle())
                    .author1(getAuthor1())
                    .author2(getAuthor2())
                    .translator(getTranslator())
                    .publisher(getPublisher())
                    .build();
        } else if (dataType == CsvDataType.UPDATE && !Objects.isNull(bookModel)) {
            return bookModel.toBuilder()
                    .subTitle(getSubTitle())
                    .author1(getAuthor1())
                    .author2(getAuthor2())
                    .translator(getTranslator())
                    .publisher(getPublisher())
                    .build();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSortValue() {
        if (Strings.isEmpty(bookCode)) {
            return DUMMY_BOOK_CODE;
        }
        return bookCode;
    }
}
