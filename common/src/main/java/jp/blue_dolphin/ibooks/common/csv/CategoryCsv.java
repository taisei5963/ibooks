package jp.blue_dolphin.ibooks.common.csv;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.annotation.constraint.CsvLengthMax;
import com.github.mygreen.supercsv.annotation.constraint.CsvPattern;
import com.github.mygreen.supercsv.annotation.constraint.CsvRequire;
import com.github.mygreen.supercsv.builder.BuildCase;
import jp.blue_dolphin.ibooks.common.constant.CsvDataType;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.model.CategoryModel;
import lombok.Getter;
import lombok.Setter;

/**
 * カテゴリCSV
 */
@CsvBean(header = true)
@Getter
@Setter
public class CategoryCsv implements CsvRow {
    @CsvColumn(label = "データ区分", number = 1)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvPattern(regex = SystemRegex.ADD_UPDATE_REGEX, message = "{csv.datatype.addUpdate.pattern}", cases = BuildCase.Read)
    private String csvDataType;

    @CsvColumn(label = "カテゴリコード", number = 2)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvLengthMax(value = 8, cases = BuildCase.Read)
    @CsvPattern(regex = SystemRegex.CATEGORY_CODE_REGEX, message = "{csv.categoryCode.pattern}", cases = BuildCase.Read)
    private String categoryCode;

    @CsvColumn(label = "カテゴリ名", number = 3)
    @CsvRequire(considerBlank = true, cases = BuildCase.Read)
    @CsvLengthMax(value = 32, cases = BuildCase.Read)
    private String categoryName;

    @CsvColumn(label = "登録日時", number = 4)
    private String createdAt;

    @CsvColumn(label = "更新日時", number = 5)
    private String updatedAt;

    @CsvColumn(label = "登録者ID", number = 6)
    private String createdId;

    /** 行数 */
    private Integer rowNum;

    /** カテゴリモデル（キャッシュ用） */
    private CategoryModel categoryModel;

    /**
     * カテゴリCSVをカテゴリモデルに変換する
     *
     * @return カテゴリモデル
     */
    public CategoryModel toModel() {
        CsvDataType dataType = CsvDataType.getEnum(csvDataType);
        if (dataType == CsvDataType.ADD) {
            return CategoryModel.builder()
                    .categoryCode(getCategoryCode())
                    .categoryName(getCategoryName())
                    .build();
        } else if (dataType == CsvDataType.UPDATE && categoryModel != null) {
            return categoryModel.toBuilder()
                    .categoryName(getCategoryName())
                    .build();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSortValue() {
        return categoryCode;
    }
}
