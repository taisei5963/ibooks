package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import jp.blue_dolphin.ibooks.common.csv.annotation.CsvDate;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * 日付フォーマットFactory
 */
public class CsvDateFactory implements ConstraintProcessorFactory<CsvDate> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvDate anno, Optional<CellProcessor> next,
                                          FieldAccessor field, TextFormatter<?> formatter,
                                          Configuration config) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvDateValidator validator = next.map(n -> new CsvDateValidator(anno.format(), n))
                .orElseGet(() -> new CsvDateValidator(anno.format()));
        return Optional.of(validator);
    }
}
