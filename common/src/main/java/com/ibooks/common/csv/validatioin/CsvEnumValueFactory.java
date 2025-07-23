package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import com.ibooks.common.csv.annotation.CsvEnumValue;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * CSV用列挙型の値チェックファクトリー
 */
@Component
public class CsvEnumValueFactory implements ConstraintProcessorFactory<CsvEnumValue> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvEnumValue anno, Optional<CellProcessor> next,
                                          FieldAccessor field, TextFormatter<?> formatter,
                                          Configuration config) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvEnumValueValidator processor = next.map(
                n -> new CsvEnumValueValidator(anno.value(), anno.useDescription(), n)).orElseGet(
                () -> new CsvEnumValueValidator(anno.value(), anno.useDescription()));
        return Optional.of(processor);
    }
}
