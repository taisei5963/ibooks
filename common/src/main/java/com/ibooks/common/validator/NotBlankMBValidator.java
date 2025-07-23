package com.ibooks.common.validator;

import com.ibooks.common.annotation.NotBlankMB;
import com.ibooks.common.util.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 入力された値に null、空文字、空白文字以外が含まれている場合は true を返却する<br>
 * ※マルチバイト対応
 */
public class NotBlankMBValidator implements ConstraintValidator<NotBlankMB, String> {
    /** エラーメッセージ */
    private String message = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(NotBlankMB constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        message = constraintAnnotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Strings.isEmpty(value)) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
