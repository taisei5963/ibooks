package jp.blue_dolphin.ibooks.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.blue_dolphin.ibooks.common.constant.MapValue;
import jp.blue_dolphin.ibooks.common.validator.EnumValueValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が対象の列挙型の値に該当するかをチェックする<br>
 * 必須ではない
 */
@Constraint(validatedBy = EnumValueValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValue {
    /** MapValue を実装した Enum クラス */
    Class<? extends Enum<? extends MapValue>> value();
    /** 値の代わりに説明を使用するかどうか */
    boolean useDescription() default false;
    /** エラーメッセージ */
    String message() default "{errors.enumValue}";
    /** グループ */
    Class<?>[] groups() default {};
    /** メタ情報 */
    Class<? extends Payload>[] payload() default {};
}
