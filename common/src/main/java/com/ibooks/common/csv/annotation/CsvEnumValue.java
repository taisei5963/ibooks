package com.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.ibooks.common.constants.MapValue;
import com.ibooks.common.csv.validatioin.CsvEnumValueFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が対象の列挙型の値に該当するかをチェックする<br>
 * 未必須です。
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CsvConstraint({CsvEnumValueFactory.class})
public @interface CsvEnumValue {
    /** 引数のクラス */
    Class<? extends Enum<? extends MapValue>> value();
    /** 値の代わりに説明を使用するかどうか */
    boolean useDescription() default false;
    BuildCase[] cases() default {};
    Class<?>[] groups() default {};
    int order() default 0;
}
