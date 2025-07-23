package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import com.ibooks.common.csv.annotation.CsvCheckScript;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * スクリプトチェックファクトリー
 */
@Component
public class CsvCheckScriptFactory implements ConstraintProcessorFactory<CsvCheckScript> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvCheckScript anno, Optional<CellProcessor> next, FieldAccessor field, TextFormatter<?> formatter, Configuration config) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvCheckScriptValidator processor = next.map(n -> new CsvCheckScriptValidator(n)).orElseGet(() -> new CsvCheckScriptValidator());
        return Optional.of(processor);
    }
}
