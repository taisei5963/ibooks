package jp.blue_dolphin.ibooks.common.csv;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.annotation.constraint.CsvLengthMax;
import com.github.mygreen.supercsv.annotation.constraint.CsvPattern;
import com.github.mygreen.supercsv.annotation.constraint.CsvRequire;
import com.github.mygreen.supercsv.builder.BuildCase;
import jp.blue_dolphin.ibooks.common.constant.CsvDataType;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ブックCSV
 */
@CsvBean(header = true)
@Getter
@Setter
public class BookCsv implements CsvRow, BookForeignKeyCsv {
    /** 登録済みの画像ファイルのカラム出力値 */
    public static final String IMAGE_REGISTERED = "登録済";

    @CsvColumn(label = "データ区分", number = 1)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvPattern(regex = SystemRegex.ADD_UPDATE_REGEX, message = "{csv.datatype.addUpdate.pattern}", cases = BuildCase.Read)
    private String csvDataType;

    @CsvColumn(label = "JANコード", number = 2)
    @CsvPattern(regex = SystemRegex.JAN_CODE_REGEX, message = "{csv.janCode.patter}", cases = BuildCase.Read)
    private String janCode;

    @CsvColumn(label = "タイトル", number = 3)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvLengthMax(value = 80, cases = BuildCase.Read)
    private String title;

    @CsvColumn(label = "サブタイトル", number = 4)
    @CsvLengthMax(value = 80, cases = BuildCase.Read)
    private String subTitle;

    @CsvColumn(label = "著者1", number = 5)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvLengthMax(value = 20, cases = BuildCase.Read)
    private String author1;

    @CsvColumn(label = "著者2", number = 6)
    @CsvLengthMax(value = 20, cases = BuildCase.Read)
    private String author2;

    @CsvColumn(label = "翻訳者", number = 7)
    @CsvLengthMax(value = 20, cases = BuildCase.Read)
    private String translator;

    @CsvColumn(label = "出版社", number = 8)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvLengthMax(value = 50, cases = BuildCase.Read)
    private String publisher;

    @CsvColumn(label = "画像ファイル名", number = 9)
    @CsvPattern(regex = SystemRegex.BOOK_IMG_REGEX, message = "{csv.bookImg.pattern}", cases = BuildCase.Read)
    private String picFileName;

    @CsvColumn(label = "カテゴリコード1", number = 10)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvPattern(regex = SystemRegex.CATEGORY_CODE_REGEX, message = "{csv.categoryCode.pattern}", cases = BuildCase.Read)
    private String categoryCode1;

    @CsvColumn(label = "カテゴリコード2", number = 11)
    @CsvPattern(regex = SystemRegex.CATEGORY_CODE_REGEX, message = "{csv.categoryCode.pattern}", cases = BuildCase.Read)
    private String categoryCode2;

    @CsvColumn(label = "カテゴリコード3", number = 12)
    @CsvPattern(regex = SystemRegex.CATEGORY_CODE_REGEX, message = "{csv.categoryCode.pattern}", cases = BuildCase.Read)
    private String categoryCode3;

    @CsvColumn(label = "登録日時", number = 13)
    private String createdAt;

    @CsvColumn(label = "更新日時", number = 14)
    private String updatedAt;

    @CsvColumn(label = "登録者ID", number = 15)
    private String createdId;

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
        List<Long> categoryIds = new ArrayList<>();
        if (!Objects.isNull(categoryId1)) {
            categoryIds.add(categoryId1);
        }
        if (!Objects.isNull(categoryId2) && !categoryIds.contains(categoryId2)) {
            categoryIds.add(categoryId2);
        }
        if (!Objects.isNull(categoryId3) && !categoryIds.contains(categoryId3)) {
            categoryIds.add(categoryId3);
        }
        return categoryIds;
    }

    /**
     * このブックCSVをブックモデルに変換する<br>
     * ※画像ファイルはコピー処理時にリネームするため、モデルにはCSVの値を設定しない
     *
     * @return ブックモデル
     */
    public BookModel toModel() {
        CsvDataType dataType = CsvDataType.getEnum(csvDataType);
        if (dataType == CsvDataType.ADD) {
            return BookModel.builder()
                    .janCode(getJanCode())
                    .title(getTitle())
                    .subTitle(getSubTitle())
                    .author1(getAuthor1())
                    .author2(getAuthor2())
                    .translator(getTranslator())
                    .publisher(getPublisher())
                    .categoryCode1(getCategoryCode1())
                    .categoryCode2(getCategoryCode2())
                    .categoryCode3(getCategoryCode3())
                    .build();
        } else if (dataType == CsvDataType.UPDATE && bookModel != null) {
            return bookModel.toBuilder()
                    .title(getTitle())
                    .subTitle(getSubTitle())
                    .author1(getAuthor1())
                    .author2(getAuthor2())
                    .translator(getTranslator())
                    .publisher(getPublisher())
                    .categoryCode1(getCategoryCode1())
                    .categoryCode2(getCategoryCode2())
                    .categoryCode3(getCategoryCode3())
                    .build();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSortValue() {
        if (Strings.isEmpty(janCode)) {
            return "0000000000001";
        }
        return janCode;
    }
}
