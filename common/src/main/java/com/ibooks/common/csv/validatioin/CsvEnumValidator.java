package com.ibooks.common.csv.validatioin;

import com.github.mygreen.supercsv.cellprocessor.ValidationCellProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 入力された値が対象の列挙型に該当するかどうかのチェックバリデーター
 */
public class CsvEnumValidator extends ValidationCellProcessor implements StringCellProcessor {
    /** 引数のクラス */
    private Class<? extends Enum<?>> cls = null;

    /**
     * コンストラクタ
     *
     * @param cls MapValueを実装した列挙型
     */
    public CsvEnumValidator(final Class<? extends Enum<?>> cls) {
        this.cls = cls;
    }

    /**
     * コンストラクタ
     *
     * @param cls  MapValueを実装した列挙型
     * @param next 次の処理
     */
    public CsvEnumValidator(final Class<? extends Enum<?>> cls, final CellProcessor next) {
        super(next);
        this.cls = cls;
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
        for (Enum<?> e : cls.getEnumConstants()) {
            if (e.name().equals(result)) {
                return next.execute(value, context);
            }
            sj.add(e.name());
        }
        throw createValidationException(context).messageFormat("列挙型の値が不正です。").messageVariables("values",
                sj.toString()).build();
    }
}
