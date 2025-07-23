package com.ibooks.common.validator;

import com.ibooks.common.annotation.CompatibleSJIS;
import com.ibooks.common.config.CommonConfig;
import com.ibooks.common.util.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.util.Objects;

/**
 * 入力された値が Windows-31J と互換性があるかチェックする。<br>
 * 未必須です。
 */
public class CompatibleSJISValidator implements ConstraintValidator<CompatibleSJIS, String> {
    /** エラーメッセージ */
    private String message = null;

    private CommonConfig commonConfig = null;

    /**
     * コンストラクタ
     *
     * @param commonConfig 共通設定
     */
    public CompatibleSJISValidator(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(CompatibleSJIS constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
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
        if (this.commonConfig == null || !this.commonConfig.isCompatibleSJIS()) {
            return true;
        }
        String str = Strings.searchIncompatibleChar(value);
        if (Objects.isNull(str)) {
            return true;
        }
        HibernateConstraintValidatorContext hibernateContext = context.unwrap(
                HibernateConstraintValidatorContext.class);
        hibernateContext.disableDefaultConstraintViolation();
        hibernateContext.addMessageParameter("char", str).buildConstraintViolationWithTemplate(
                message).addConstraintViolation();
        return false;
    }
}
