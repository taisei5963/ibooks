package com.ibooks.common.validator;

import com.ibooks.common.annotation.CheckScript;
import com.ibooks.common.util.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.util.Objects;

/**
 * 入力された値にスクリプトと疑わしい単語があるをチェックする。<br>
 * 未必須です。
 */
public class CheckScriptValidator implements ConstraintValidator<CheckScript, String> {
    /** エラーメッセージ */
    private String message = null;

    /** スクリプトの検知用単語リスト */
    private static final String[] SCRIPT_WORDS =
            {"script", "onclick", "ondblclick", "onmousedown", "onmouseup",
                    "onmouseover", "onmousemove", "onmouseout", "onkeypress", "onkeydown",
                    "onkeyup", "onload", "onunload",
                    "onbeforeunload", "onfocus", "onblur", "onsubmit", "onreset", "onselect",
                    "onchange", "oninput", "onresize",
                    "onscroll", "onmove", "onabort", "onerror"};

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(CheckScript constraintAnnotation) {
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
        String word = getScriptWord(value);
        if (Objects.isNull(word)) {
            return true;
        }
        HibernateConstraintValidatorContext hibernateContext = context.unwrap(
                HibernateConstraintValidatorContext.class);
        hibernateContext.disableDefaultConstraintViolation();
        hibernateContext.addMessageParameter("word", word)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
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
