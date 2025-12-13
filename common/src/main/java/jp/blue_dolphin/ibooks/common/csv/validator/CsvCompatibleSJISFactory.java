package jp.blue_dolphin.ibooks.common.csv.validator;

import com.github.mygreen.supercsv.builder.Configuration;
import com.github.mygreen.supercsv.builder.FieldAccessor;
import com.github.mygreen.supercsv.cellprocessor.ConstraintProcessorFactory;
import com.github.mygreen.supercsv.cellprocessor.format.TextFormatter;
import jp.blue_dolphin.ibooks.common.config.CommonConfig;
import jp.blue_dolphin.ibooks.common.csv.annotation.CsvCompatibleSJIS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Optional;

/**
 * Windows-31J 互換性チェックFactory
 */
@Component
public class CsvCompatibleSJISFactory  implements ConstraintProcessorFactory<CsvCompatibleSJIS> {
    /** システム共通設定 */
    @Autowired
    private CommonConfig commonConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellProcessor> create(CsvCompatibleSJIS anno, Optional<CellProcessor> next,
                                          FieldAccessor field, TextFormatter<?> formatter,
                                          Configuration configuration) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return next;
        }
        final CsvCompatibleSJISValidator validator =
                next.map(n -> new CsvCompatibleSJISValidator(commonConfig.isCompatibleSJIS(), n))
                        .orElseGet(() -> new CsvCompatibleSJISValidator(
                                commonConfig.isCompatibleSJIS()));
        return Optional.of(validator);
    }
}
