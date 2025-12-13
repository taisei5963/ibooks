package jp.blue_dolphin.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import jp.blue_dolphin.ibooks.common.csv.validator.CsvCheckScriptFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力されたアタにスクリプトと疑わしい単語があるかチェックする<br>
 * 必須ではない
 */
@CsvConstraint(CsvCheckScriptFactory.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface CsvCheckScript {
    /** ケース */
    BuildCase[] cases() default {};
    /** グループ */
    Class<?>[] groups() default {};
    /** 運用順 */
    int order() default 0;
}
