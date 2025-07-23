package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import com.ibooks.common.validator.CheckScriptValidator;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;

/**
 * 入力された値にスクリプトと疑わしい単語があるかのチェックバリデーター
 */
public class CsvCheckScriptValidator extends ValidationCellProcessor implements StringCellProcessor {
    /**
     * コンストラクタ
     */
    public CsvCheckScriptValidator() {}

    /**
     * コンストラクタ
     *
     * @param next 次の処理
     */
    public CsvCheckScriptValidator(final CellProcessor next) {
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
        String word = CheckScriptValidator.getScriptWord(result);
        if (Objects.isNull(word)) {
            return next.execute(value, context);
        }
        throw createValidationException(context).messageFormat("値にスクリプトと疑われる文字列が含まれています。")
                .messageVariables("word", word)
                .build();
    }
}
