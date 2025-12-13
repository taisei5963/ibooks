package jp.blue_dolphin.ibooks.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.blue_dolphin.ibooks.common.annotation.CompatibleSJIS;
import jp.blue_dolphin.ibooks.common.config.CommonConfig;
import jp.blue_dolphin.ibooks.common.util.Strings;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

/**
 * 入力された値がWindows-31J と互換があるかどうかをチェックする<br>
 * 必須ではない
 */
public class CompatibleSJISValidator implements ConstraintValidator<CompatibleSJIS, String> {
    /** エラーメッセージ */
    private String message = null;

    private CommonConfig commonConfig = null;

    /**
     * コンストラクタ
     *
     * @param commonConfig システム共通設定
     */
    public CompatibleSJISValidator(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(CompatibleSJIS annotation) {
        ConstraintValidator.super.initialize(annotation);
        message = annotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isBlank(value)) {
            return true;
        }
        if (this.commonConfig == null || this.commonConfig.isCompatibleSJIS()) {
            return true;
        }
        String str = Strings.searchIncompatibleChar(value);
        if (str == null) {
            return true;
        }
        HibernateConstraintValidatorContext hibernateContext = context.unwrap(
                HibernateConstraintValidatorContext.class);
        hibernateContext.disableDefaultConstraintViolation();
        hibernateContext.addMessageParameter("char", str)
                .buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
