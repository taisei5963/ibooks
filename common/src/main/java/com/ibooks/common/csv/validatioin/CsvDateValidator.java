package com.ibooks.common.csv.validatioin;

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
 * 入力された値が指定の日付のフォーマットかどうかのチェックバリデーター
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
     * @param format フォーマット
     * @param next   次の処理
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
            DateTimeFormatter.ofPattern(this.format).withResolverStyle(ResolverStyle.STRICT).parse(result,
                    LocalDate::from);
        } catch (DateTimeParseException e) {
            throw createValidationException(context).messageFormat("値が日付形式" + this.format + "になっていません")
                    .messageVariables("format", this.format)
                    .build();
        }
        return next.execute(value, context);
    }
}
