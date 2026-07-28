package com.techcof.ITive.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.techcof.ITive.common.annotation.EnumValue;
import com.techcof.ITive.common.constant.MapValue;
import com.techcof.ITive.common.util.Strings;

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
    public void initialize(EnumValue annotation) {
        ConstraintValidator.super.initialize(annotation);
        cls = annotation.value();
        useDescription = annotation.useDescription();
        message = annotation.message();
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
