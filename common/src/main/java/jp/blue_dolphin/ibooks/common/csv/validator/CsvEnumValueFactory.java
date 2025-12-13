package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import jp.blue_dolphin.ibooks.common.csv.annotation.CsvEnumValue;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * CSV用列挙型の値チェックFactory
 */
public class CsvEnumValueFactory implements ConstraintProcessorFactory<CsvEnumValue> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvEnumValue anno, Optional<CellProcessor> next,
                                          FieldAccessor field, TextFormatter<?> formatter,
                                          Configuration configuration) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvEnumValueValidator validator =
                next.map(n -> new CsvEnumValueValidator(anno.value(), anno.useDescription(), n))
                        .orElseGet(() -> new CsvEnumValueValidator(anno.value(),
                                anno.useDescription()));
        return Optional.of(validator);
    }
}
