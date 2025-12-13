package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;

/**
 * 入力された文字に null, 空文字, 空白文字以外の文字があるかチェックする<br>
 * ※マルチバイト対応
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
        if (!Objects.isNull(value)) {
            final String result;
            if (value instanceof String) {
                result = (String) value;
            } else {
                throw new SuperCsvCellProcessorException(String.class, value, context, this);
            }
            if (!result.isEmpty()) {
                return next.execute(value, context);
            }
        }
        throw createValidationException(context)
                .messageFormat("必須です。")
                .build();
    }
}
