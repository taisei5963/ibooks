package com.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.ibooks.common.csv.validatioin.CsvDateFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が指定した日付のフォーマットかどうかをチェックする<br>
 * 未必須です。
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CsvConstraint({CsvDateFactory.class})
public @interface CsvDate {
    /** フォーマット */
    String format();
    BuildCase[] cases() default {};
    Class<?>[] groups() default {};
    int order() default 0;
}
