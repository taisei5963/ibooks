package com.ibooks.common.annotation;

import com.ibooks.common.validator.CompatibleSJISValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値が Windows-31J と互換性があるかチェックする。<br>
 * 未必須です。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CompatibleSJISValidator.class)
public @interface CompatibleSJIS {
    /** エラーメッセージ */
    String message() default "{errors.incompatibleSJIS}";

    /** グループ */
    Class<?>[] groups() default {};

    /** メタ情報 */
    Class<? extends Payload>[] payload() default {};
}
