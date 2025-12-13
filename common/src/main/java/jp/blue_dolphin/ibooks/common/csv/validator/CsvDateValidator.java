package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;

/**
 * 入力された値が指定した日付のフォーマットかどうかをチェックする<br>
 * 必須ではない
 */
public class CsvDateValidator extends ValidationCellProcessor implements StringCellProcessor {
    /** フォーマット */
    private final String format;

    /**
     * コンストラクタ
     *
     * @param format フォーマット
     */
    public CsvDateValidator(final String format) {
        this.format = format;
    }

    /**
     * コンストラクタ
     *
     * @param format
     * @param next
     */
    public CsvDateValidator(final String format, final CellProcessor next) {
        super(next);
        this.format = format;
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
        try {
            DateTimeFormatter.ofPattern(this.format).withResolverStyle(ResolverStyle.STRICT)
                    .parse(result, LocalDate::from);
        } catch (DateTimeParseException e) {
            throw createValidationException(context)
                    .messageFormat("値が日付形式" + this.format + "ではありません。")
                    .messageVariables("format", this.format)
                    .build();
        }
        return next.execute(value, context);
    }
}
