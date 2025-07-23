package com.ibooks.common.validator;

import com.ibooks.common.annotation.EnumValue;
import com.ibooks.common.constants.MapValue;
import com.ibooks.common.util.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 入力された値が対象の列挙型の値に該当するかをチェックする<br>
 * 未必須です
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {
    /** 引数のクラス */
    private Class<? extends Enum<? extends MapValue>> cls = null;
    /** 値の代わりに説明を使用するかどうか */
    private boolean useDescription = false;
    /** エラーメッセージ */
    private String message = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        cls = constraintAnnotation.value();
        useDescription = constraintAnnotation.useDescription();
        message = constraintAnnotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isEmpty(value)) {
            return true;
        }
        for (Enum<? extends MapValue> e : cls.getEnumConstants()) {
            MapValue mv = (MapValue) e;
            if (!useDescription && mv.getValue().equals(value)) {
                return true;
            }
            if (useDescription && mv.getDescription().equals(value)) {
                return true;
            }
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
