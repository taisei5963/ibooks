package jp.blue_dolphin.ibooks.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.blue_dolphin.ibooks.common.validator.CompatibleSJISValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値がWindows-31J と互換があるかどうかをチェックする<br>
 * 必須ではない
 */
@Constraint(validatedBy = CompatibleSJISValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompatibleSJIS {
    /** エラーメッセージ */
    String message() default "{errors.incompatibleSJIS}";
    /** グループ */
    Class<?>[] groups() default {};
    /** メタ情報 */
    Class<? extends Payload>[] payload() default {};
}
