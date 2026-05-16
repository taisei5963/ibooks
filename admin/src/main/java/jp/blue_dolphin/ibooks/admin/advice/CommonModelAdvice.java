package jp.blue_dolphin.ibooks.admin.advice;

import jp.blue_dolphin.ibooks.admin.dto.AdminSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 共通モデルクラス
 */
@ControllerAdvice
@RequiredArgsConstructor
public class CommonModelAdvice {
    private final AdminSession adminSession;

    /**
     * 管理者セッション情報を返却する
     *
     * @return 管理者セッション情報
     */
    @ModelAttribute("adminDto")
    public AdminSession adminSession() {
        return adminSession;
    }
}
