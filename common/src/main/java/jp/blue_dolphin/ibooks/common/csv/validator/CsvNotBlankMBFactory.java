package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import jp.blue_dolphin.ibooks.common.csv.annotation.CsvNotBlankMB;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * 空白チェックFactory
 */
@Component
public class CsvNotBlankMBFactory implements ConstraintProcessorFactory<CsvNotBlankMB> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvNotBlankMB anno, Optional<CellProcessor> next,
                                          FieldAccessor field, TextFormatter<?> formatter,
                                          Configuration configuration) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvNotBlankMBValidator validator = next.map(n -> new CsvNotBlankMBValidator(n))
                .orElseGet(() -> new CsvNotBlankMBValidator());
        return Optional.of(validator);
    }
}
