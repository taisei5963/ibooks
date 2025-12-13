package jp.blue_dolphin.ibooks.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jp.blue_dolphin.ibooks.common.constant.ErrorMessages;
import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * パスワード変更フォーム
 */
@Data
public class UpdatePasswordForm {
    /** 新しいパスワード */
    @NotBlank(message = ErrorMessages.REQUIRED)
    @Length(max = 20, message = ErrorMessages.MAX_LENGTH)
    @Pattern(regexp = SystemRegex.PASSWORD_REGEX, message = ErrorMessages.PASSWORD_REGEX)
    private String newPassword;

    /** 新しいパスワード（確認用） */
    @NotBlank(message = ErrorMessages.REQUIRED)
    @Length(max = 20, message = ErrorMessages.MAX_LENGTH)
    @Pattern(regexp = SystemRegex.PASSWORD_REGEX, message = ErrorMessages.PASSWORD_REGEX)
    private String confirmPassword;

    /** バージョン情報 */
    public Integer ver;
}
