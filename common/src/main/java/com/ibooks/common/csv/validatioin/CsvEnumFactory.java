package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import com.ibooks.common.csv.annotation.CsvEnum;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * CSV用列挙型チェックファクトリー
 */
@Component
public class CsvEnumFactory implements ConstraintProcessorFactory<CsvEnum> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvEnum anno, Optional<CellProcessor> next, FieldAccessor field,
                                          TextFormatter<?> formatter, Configuration config) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvEnumValidator processor = next.map(n -> new CsvEnumValidator(anno.value(), n)).orElseGet(
                () -> new CsvEnumValidator(anno.value()));
        return Optional.of(processor);
    }
}
