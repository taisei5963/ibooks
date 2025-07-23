package com.ibooks.admin.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * ログインフォーム
 */
@Data
public class LoginForm {
    /** ログインID */
    @NotBlank
    @Length(max = 30)
    private String loginId;

    /** パスワード */
    @NotBlank
    private String password;
}
