package jp.blue_dolphin.ibooks.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.blue_dolphin.ibooks.common.validator.CheckScriptValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入力された値にスクリプトと疑わしい単語があるかをチェックする<br>
 * 必須ではない
 */
@Constraint(validatedBy = CheckScriptValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CheckScript {
    /** エラーメッセージ */
    String message() default "{errors.checkScript}";
    /** グループ */
    Class<?>[] groups() default {};
    /** メタ情報 */
    Class<? extends Payload>[] payload() default {};
}
