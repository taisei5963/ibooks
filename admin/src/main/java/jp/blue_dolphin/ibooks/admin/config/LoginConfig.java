package jp.blue_dolphin.ibooks.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * ログイン設定
 */
@Component
@ConfigurationProperties("admin-config.login")
@Data
public class LoginConfig {
    /** 最大試行回数 */
    private Integer failureLimit;
    /** ログインロック時間（秒） */
    private Integer failureLockSec;
    /** 二段階認証の有効・無効 */
    private Boolean twoFactorAuth;

    /**
     * 二段階認証が有効な場合は true を返却する
     *
     * @return {@code true} 二段階認証が有効
     */
    public boolean enableTowFactorAuth() {
        return Objects.requireNonNullElse(this.twoFactorAuth, false);
    }
}
