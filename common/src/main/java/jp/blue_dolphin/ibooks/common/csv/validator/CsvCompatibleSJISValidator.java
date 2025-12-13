package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import jp.blue_dolphin.ibooks.common.util.Strings;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;

/**
 * Windows-31J 互換性バリデーター
 */
public class CsvCompatibleSJISValidator extends ValidationCellProcessor
        implements StringCellProcessor {
    /** 互換性があるかどうか */
    private boolean compatible = false;

    /**
     * コンストラクタ
     *
     * @param compatible {@code true} Window-31J互換
     */
    public CsvCompatibleSJISValidator(boolean compatible) {
        this.compatible = compatible;
    }

    /**
     * コンストラクタ
     *
     * @param compatible @param compatible {@code true} Window-31J互換
     * @param next       次の処理
     */
    public CsvCompatibleSJISValidator(boolean compatible, final CellProcessor next) {
        super(next);
        this.compatible = compatible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T execute(Object value, CsvContext context) {
        if (Objects.isNull(value)) {
            return next.execute(value, context);
        }
        final String result;
        if (value instanceof String) {
            result = (String) value;
        } else {
            throw new SuperCsvCellProcessorException(String.class, value, context, this);
        }
        if (result.isEmpty()) {
            return next.execute(value, context);
        }
        if (!this.compatible) {
            return next.execute(value, context);
        }
        String str = Strings.searchIncompatibleChar(result);
        if (Objects.isNull(str)) {
            return next.execute(value, context);
        }
        throw createValidationException(context)
                .messageFormat("値にSHIFT-JISと互換性のない文字が含まれています。")
                .messageVariables("char", str)
                .build();
    }
}
