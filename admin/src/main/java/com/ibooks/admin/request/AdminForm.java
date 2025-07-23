package com.ibooks.admin.request;

import com.ibooks.common.constants.Regex;
import com.ibooks.common.constants.ValidationErrorMessages;
import com.ibooks.common.csv.annotation.CsvCompatibleSJIS;
import com.ibooks.common.model.AdminModel;
import com.ibooks.common.validation.group.Insert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 管理者フォーム
 */
@Data
public class AdminForm {
    /** 管理者ID */
    private Long adminID;

    /** 管理者ログインID */
    @NotBlank(message = ValidationErrorMessages.REQUIRED, groups = Insert.class)
    @Length(max = 30, message = ValidationErrorMessages.MAX_LENGTH)
    @Pattern(regexp = Regex.CODE_REGEX, message = ValidationErrorMessages.CODE_REGEX)
    private String loginId;

    /** 管理者パスワード */
    @NotBlank(message = ValidationErrorMessages.REQUIRED, groups = Insert.class)
    @Length(max = 30, message = ValidationErrorMessages.MAX_LENGTH)
    @Pattern(regexp = Regex.PASSWORD_REGEX, message = ValidationErrorMessages.PASSWORD_REGEX)
    private String password;

    /** 管理者名 */
    @NotBlank(message = ValidationErrorMessages.REQUIRED, groups = Insert.class)
    @Length(max = 100, message = ValidationErrorMessages.MAX_LENGTH)
    @CsvCompatibleSJIS
    private String name;

    /** 更新者ID */
    private String registerId;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** バージョン */
    public Integer ver;

    /**
     * モデルからフォームへ変換
     *
     * @param model 管理者モデル
     * @return 管理者フォーム
     */
    public static AdminForm of(AdminModel model) {
        AdminForm form = new AdminForm();
        form.setAdminID(model.getAdminId());
        form.setLoginId(model.getLoginId());
        form.setName(model.getName());
        form.setRegisterId(model.getCreateId());
        form.setUpdatedAt(model.getUpdateDate());
        form.setVer(model.getVer());
        return form;
    }

    /**
     * 更新処理可新規登録処理化を判定する
     *
     * @return {@code true} 新規登録処理 {@code false} 更新処理
     */
    public boolean isCreate() {
        return this.getAdminID() == null;
    }
}
