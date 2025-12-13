package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import jp.blue_dolphin.ibooks.common.csv.annotation.CsvEmail;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * CSV用メールアドレスチェックFactory
 */
public class CsvEmailFactory implements ConstraintProcessorFactory<CsvEmail> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvEmail anno, Optional<CellProcessor> next,
                                          FieldAccessor field, TextFormatter<?> formatter,
                                          Configuration configuration) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvEmailValidator validator = next.map(n -> new CsvEmailValidator(n))
                .orElseGet(() -> new CsvEmailValidator());
        return Optional.of(validator);
    }
}
