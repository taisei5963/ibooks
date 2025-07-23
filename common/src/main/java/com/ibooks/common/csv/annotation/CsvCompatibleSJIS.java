package com.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.ibooks.common.csv.validatioin.CsvCheckScriptFactory;
import com.ibooks.common.csv.validatioin.CsvCompatibleSJISFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が Windows-31J と互換性があるかチェックする。<br>
 * 未必須です。
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CsvConstraint(CsvCompatibleSJISFactory.class)
public @interface CsvCompatibleSJIS {
    /** ケース */
    BuildCase[] cases() default {};

    /** グループ */
    Class<?>[] groups() default {};

    /** 適用順 */
    int order() default 0;
}
