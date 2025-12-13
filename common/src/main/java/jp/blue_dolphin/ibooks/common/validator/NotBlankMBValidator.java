package jp.blue_dolphin.ibooks.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.blue_dolphin.ibooks.common.annotation.NotBlankMB;
import jp.blue_dolphin.ibooks.common.util.Strings;

/**
 * 入力された値に null、空文字、空白文字以外の文字があるかチェックする<br>
 * ※マルチバイト対応
 */
public class NotBlankMBValidator implements ConstraintValidator<NotBlankMB, String> {
    /** エラーメッセージ */
    private String message = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(NotBlankMB annotation) {
        ConstraintValidator.super.initialize(annotation);
        message = annotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Strings.isBlank(value)) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
