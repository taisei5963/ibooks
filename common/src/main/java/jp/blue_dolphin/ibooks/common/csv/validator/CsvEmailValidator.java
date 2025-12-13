package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import jp.blue_dolphin.ibooks.common.util.Strings;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;

/**
 * 入力された値がメールアドレスとして正しいかどうかをチェックする<br>
 * 必須ではない
 */
public class CsvEmailValidator extends ValidationCellProcessor implements StringCellProcessor {
    /**
     * コンストラクタ
     */
    public CsvEmailValidator() {
    }

    /**
     * コンストラクタ
     *
     * @param next 次の処理
     */
    public CsvEmailValidator(final CellProcessor next) {
        super(next);
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
        if (Strings.validateEmail(result)) {
            return next.execute(value, context);
        }
        throw createValidationException(context)
                .messageFormat("値がメールアドレス形式ではありません。")
                .build();
    }
}