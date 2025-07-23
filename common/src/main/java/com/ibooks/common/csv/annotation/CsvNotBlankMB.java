package com.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.ibooks.common.csv.validatioin.CsvNotBlankMBFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値に null、空文字、空白文字以外の文字があるかをチェックする<br>
 * ※マルチバイト対応
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CsvConstraint({CsvNotBlankMBFactory.class})
public @interface CsvNotBlankMB {
    /** ケース */
    BuildCase[] cases() default {};
    /** グループ */
    Class<?>[] groups() default {};
    /** 適当順 */
    int order() default 0;
}
