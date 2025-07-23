package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import com.ibooks.common.constants.MapValue;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 入力された値が対象の列挙型に該当するかどうかのチェックバリデーター
 */
public class CsvEnumValueValidator extends ValidationCellProcessor implements StringCellProcessor {
    /** 引数のクラス */
    private Class<? extends Enum<? extends MapValue>> cls = null;
    /** 値の代わりに説明を使用するかどうか */
    private boolean useDescription = false;

    /**
     * コンストラクタ
     *
     * @param cls            MapValueを実装した列挙型
     * @param useDescription {@code true} 値の代わりに説明を使用するかどうか
     */
    public CsvEnumValueValidator(final Class<? extends Enum<? extends MapValue>> cls, final boolean useDescription) {
        this.cls = cls;
        this.useDescription = useDescription;
    }

    /**
     * コンストラクタ
     *
     * @param cls  MapValueを実装した列挙型
     * @param next 次の処理
     */
    public CsvEnumValueValidator(final Class<? extends Enum<? extends MapValue>> cls, final boolean useDescription,
                                 final CellProcessor next) {
        super(next);
        this.cls = cls;
        this.useDescription = useDescription;
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
        StringJoiner sj = new StringJoiner(", ");
        for (Enum<? extends MapValue> e : cls.getEnumConstants()) {
            MapValue mv = (MapValue) e;
            if (this.useDescription) {
                if (mv.getDescription().equals(result)) {
                    return next.execute(value, context);
                }
            } else {
                if (mv.getValue().equals(result)) {
                    return next.execute(value, context);
                }
                sj.add(String.format("%s(%s)", mv.getValue(), mv.getDescription()));
            }
        }
        throw createValidationException(context).messageFormat("列挙型の値が不正です。").messageVariables("values",
                sj.toString()).build();
    }
}
