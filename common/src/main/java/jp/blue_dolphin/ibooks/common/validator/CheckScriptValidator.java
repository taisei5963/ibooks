package jp.blue_dolphin.ibooks.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.blue_dolphin.ibooks.common.annotation.CheckScript;
import jp.blue_dolphin.ibooks.common.util.Strings;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.util.Objects;

public class CheckScriptValidator implements ConstraintValidator<CheckScript, String> {
    /** エラーメッセージ */
    private String message = null;

    /** スクリプト検知用単語リスト */
    private static final String[] SCRIPT_WORDS = {
            "<script", "javascript:", "vbscript:", "data:text/html",
            "onload", "onunload", "onerror", "onfocus", "onblur",
            "onclick", "ondblclick", "onmousedown", "onmouseup",
            "onmouseover", "onmousemove", "onmouseout",
            "onkeypress", "onkeydown", "onkeyup",
            "onchange", "onsubmit", "onreset", "onselect",
            "alert(", "prompt(", "confirm(", "eval(",
            "document.", "window.", ".innerHTML", ".outerHTML", ".src"
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(CheckScript annotation) {
        ConstraintValidator.super.initialize(annotation);
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
        String word = getScriptWord(value);
        if (Objects.isNull(word)) {
            return true;
        }
        HibernateConstraintValidatorContext hibernateContext = context.unwrap(
                HibernateConstraintValidatorContext.class);

        hibernateContext.disableDefaultConstraintViolation();
        hibernateContext.addMessageParameter("word", word)
                .buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }

    /**
     * 引数の値にスクリプトと疑わしい単語が含まれている場合はその単語を返却する
     *
     * @param value 値
     * @return スクリプトと疑わしい単語
     */
    public static String getScriptWord(String value) {
        if (Strings.isEmpty(value)) {
            return null;
        }
        String word = null;
        for (String w : SCRIPT_WORDS) {
            if (value.toLowerCase().contains(w)) {
                word = w;
                break;
            }
        }
        return word;
    }
}
