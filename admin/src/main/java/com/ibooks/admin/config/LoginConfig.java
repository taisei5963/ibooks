package com.ibooks.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
}
