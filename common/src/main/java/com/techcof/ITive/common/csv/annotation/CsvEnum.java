package com.techcof.ITive.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.techcof.ITive.common.csv.validator.CsvEnumFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が対象の列挙型に該当するかどうかをチェックする<br>
 * 必須ではない
 */
@CsvConstraint(CsvEnumFactory.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface CsvEnum {
    /** 引数のクラス */
    Class<? extends Enum<?>> value();
    /** ケース */
    BuildCase[] cases() default {};
    /** グループ */
    Class<?>[] groups() default {};
    /** 運用順 */
    int order() default 0;
}
