package jp.blue_dolphin.ibooks.common.csv;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.annotation.constraint.CsvLengthMax;
import com.github.mygreen.supercsv.annotation.constraint.CsvPattern;
import com.github.mygreen.supercsv.annotation.constraint.CsvRequire;
import com.github.mygreen.supercsv.builder.BuildCase;
import jp.blue_dolphin.ibooks.common.constant.CsvDataType;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.model.BookChapterModel;
import lombok.Getter;
import lombok.Setter;

/**
 * ブックチャプターCSV
 */
@CsvBean(header = true)
@Getter
@Setter
public class BookChapterCsv implements CsvRow {
    @CsvColumn(label = "データ区分", number = 1)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvPattern(regex = SystemRegex.ADD_UPDATE_REGEX, message = "{csv.datatype.addUpdate.pattern}", cases = BuildCase.Read)
    private String csvDataType;

    @CsvColumn(label = "JANコード", number = 2)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvPattern(regex = SystemRegex.JAN_CODE_REGEX, message = "{csv.janCode.pattern}", cases = BuildCase.Read)
    private String janCode;

    @CsvColumn(label = "章", number = 3)
    @CsvLengthMax(value = 100, cases = BuildCase.Read)
    private String chapter;

    @CsvColumn(label = "タイトル", number = 4)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvLengthMax(value = 255, cases = BuildCase.Read)
    private String title;

    @CsvColumn(label = "ソート順", number = 5)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    private String sortOrder;

    @CsvColumn(label = "登録日時", number = 6)
    private String createdAt;

    @CsvColumn(label = "更新日時", number = 7)
    private String updatedAt;

    @CsvColumn(label = "登録者ID", number = 8)
    private String createdId;

    /** 行数 */
    private Integer rowNum;

    /** ブックID（キャッシュ用） */
    private Long bookId;
    /** ブックモデル（キャッシュ用） */
    private BookChapterModel bookChapterModel;

    /**
     * ブックチャプターCSVをブックチャプターモデルに変換する
     *
     * @return ブックチャプターモデル
     */
    public BookChapterModel toModel() {
        CsvDataType dataType = CsvDataType.getEnum(csvDataType);
        if (dataType == CsvDataType.ADD) {
            return BookChapterModel.builder()
                    .bookId(getBookId())
                    .chapter(getChapter())
                    .sortOrder(Integer.parseInt(getSortOrder()))
                    .title(getTitle())
                    .build();
        } else if (dataType == CsvDataType.UPDATE && bookChapterModel != null) {
            return bookChapterModel.toBuilder()
                    .chapter(getChapter())
                    .sortOrder(Integer.parseInt(getSortOrder()))
                    .title(getTitle())
                    .build();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSortValue() {
        return sortOrder;
    }
}
