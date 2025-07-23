package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import com.ibooks.common.csv.annotation.CsvNotBlankMB;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * CSV用列挙型チェックファクトリー
 */
@Component
public class CsvNotBlankMBFactory implements ConstraintProcessorFactory<CsvNotBlankMB> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvNotBlankMB anno, Optional<CellProcessor> next, FieldAccessor field,
                                          TextFormatter<?> formatter, Configuration config) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvNotBlankMBValidator processor = next.map(n -> new CsvNotBlankMBValidator(n)).orElseGet(
                () -> new CsvNotBlankMBValidator());
        return Optional.of(processor);
    }
}
