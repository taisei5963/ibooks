package jp.techcof.ITive.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.techcof.ITive.common.validator.NotBlankMBValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値に null、空文字、空白文字以外の文字があるかチェックする<br>
 * ※マルチバイト対応
 */
@Constraint(validatedBy = NotBlankMBValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotBlankMB {
    /** エラーメッセージ */
    String message() default "{errors.required}";
    /** グループ */
    Class<?>[] groups() default {};
    /** メタ情報 */
    Class<? extends Payload>[] payload() default {};
}
