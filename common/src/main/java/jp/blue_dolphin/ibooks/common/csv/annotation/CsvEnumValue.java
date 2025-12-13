package jp.blue_dolphin.ibooks.common.csv.annotation;

import com.github.mygreen.supercsv.annotation.constraint.CsvConstraint;
import com.github.mygreen.supercsv.builder.BuildCase;
import jp.blue_dolphin.ibooks.common.constant.MapValue;
import jp.blue_dolphin.ibooks.common.csv.validator.CsvEnumValueFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が対象の列挙型二該当するかどうかをチェックする<br>
 * 必須ではない
 */
@CsvConstraint(CsvEnumValueFactory.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface CsvEnumValue {
    /** 引数のクラス */
    Class<? extends Enum<? extends MapValue>> value();
    /** 値の代わりに説明を使用するかどうか */
    boolean useDescription() default false;
    /** ケース */
    BuildCase[] cases() default {};
    /** グループ */
    Class<?>[] groups() default {};
    /** 運用順 */
    int order() default 0;
}
