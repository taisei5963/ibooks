package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * 入力された値が対象の列挙型に該当するかどうかのチェックバリデーター
 */
public class CsvNotBlankMBValidator extends ValidationCellProcessor implements StringCellProcessor {

    /**
     * コンストラクタ
     */
    public CsvNotBlankMBValidator() {
    }

    /**
     * コンストラクタ
     *
     * @param next 次の処理
     */
    public CsvNotBlankMBValidator(final CellProcessor next) {
        super(next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T execute(Object value, CsvContext context) {
        if (value != null) {
            final String result;
            if (value instanceof String) {
                result = (String) value;
            } else {
                throw new SuperCsvCellProcessorException(String.class, value, context, this);
            }
            if (!result.isBlank()) {
                return next.execute(value, context);
            }
        }
        throw createValidationException(context).messageFormat("必須です。").build();
    }
}
