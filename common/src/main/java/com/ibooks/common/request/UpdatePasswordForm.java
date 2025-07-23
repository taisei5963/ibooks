package com.ibooks.common.request;

import com.ibooks.common.constants.Regex;
import com.ibooks.common.constants.ValidationErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * パスワード更新フォーム
 */
public class UpdatePasswordForm {
    /** パスワード */
    @NotBlank(message = ValidationErrorMessages.REQUIRED)
    private String password;

    /** 新しいパスワード */
    @NotBlank(message = ValidationErrorMessages.REQUIRED)
    @Length(max = 20, message = ValidationErrorMessages.MAX_LENGTH)
    @Pattern(regexp = Regex.PASSWORD_REGEX, message = ValidationErrorMessages.PASSWORD_REGEX)
    private String newPassword;

    /** バージョン情報 */
    public Integer ver;
}
