package jp.blue_dolphin.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import jp.blue_dolphin.ibooks.common.csv.validator.CsvCompatibleSJISFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が Windows-31J と互換性があるかチェックする<br>
 * 必須ではない
 */
@CsvConstraint(CsvCompatibleSJISFactory.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface CsvCompatibleSJIS {
    /** ケース */
    BuildCase[] cases() default {};
    /** グループ */
    Class<?>[] groups() default {};
    /** 運用順 */
    int order() default 0;
}
