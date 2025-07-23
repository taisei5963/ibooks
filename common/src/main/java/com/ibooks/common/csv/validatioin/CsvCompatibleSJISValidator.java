package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import com.ibooks.common.util.Strings;
import com.ibooks.common.validator.CheckScriptValidator;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;

/**
 * Windows-31J 互換性バリデーター
 */
public class CsvCompatibleSJISValidator extends ValidationCellProcessor implements StringCellProcessor {

    /**
     * Windows31-J 互換かどうか
     */
    private boolean compatible = false;

    /**
     * コンストラクタ
     *
     * @param compatible {@code true} Windows-31J 互換
     */
    public CsvCompatibleSJISValidator(boolean compatible) {
        this.compatible = compatible;
    }

    /**
     * コンストラクタ
     *
     * @param compatible {@code true} Windows-31J 互換
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
        throw createValidationException(context).messageFormat("値にSJISと互換性のない文字が含まれています。")
                .messageVariables("char", str)
                .build();
    }
}
