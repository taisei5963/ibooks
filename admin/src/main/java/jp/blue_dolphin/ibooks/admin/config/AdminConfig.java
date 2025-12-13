package jp.blue_dolphin.ibooks.admin.config;

import jp.blue_dolphin.ibooks.common.config.CommonConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.Objects;

/**
 * 管理者サイト設定
 */
@Component
public class AdminConfig implements CommonConfig {
    /** 管理者パスワード最小文字列長 */
    @Value("${admin-config.admin-password-min-length}")
    private int adminPasswordMinLength;

    /** 管理者パスワードの文字混在チェック */
    @Value("${admin-config.admin-password-check-mixed-chars}")
    private Boolean adminPasswordCheckMixedChars;

    /** レビュワーパスワード最小文字列長 */
    @Value("${admin-config.reviewer-password-min-length}")
    private int reviewerPasswordMinLength;

    /** レビュワーパスワードの文字混在チェック */
    @Value("${admin-config.reviewer-password-check-mixed-chars}")
    private Boolean reviewerPasswordCheckMixedChars;

    /** Windows-31J 互換かどうか */
    @Value("${admin-config.compatible-sjis}")
    private Boolean compatibleSJIS;

    /** 丸めモード */
    @Getter
    @Value("${admin-config.rating.rounding-mode}")
    private RoundingMode roundingMode;

    /** レビュワーサイトURL */
    @Getter
    @Value("${admin-config.reviewer-site.url}")
    private String reviewerSite;

    /** 一般サイトURL */
    @Getter
    @Value("${admin-config.general-site.url}")
    private String generalSite;

    /**
     * 管理者パスワードの最小文字列長を取得する
     *
     * @return 管理者パスワードの最小文字列長
     */
    public int getAdminPasswordMinLength() {
        // INFO: デフォルトはセキュリティ推奨値
        return Objects.requireNonNullElse(this.adminPasswordMinLength, 8);
    }

    /**
     * 管理者パスワードの文字混在チェックをするかどうか
     *
     * @return {@code true} 文字混在チェックをする
     */
    public boolean enableAdminPasswordCheckMixedChars() {
        return Objects.requireNonNullElse(this.adminPasswordCheckMixedChars, false);
    }

    /**
     * レビュワーパスワードの最小文字列長を取得する
     *
     * @return レビュワーパスワードの最小文字列長
     */
    public int getReviewerPasswordMinLength() {
        return Objects.requireNonNullElse(this.reviewerPasswordMinLength, 8);
    }

    /**
     * レビュワーパスワードの文字混在チェックをするかどうか
     *
     * @return {@code true} 文字混在チェックをする
     */
    public boolean enableReviewerPasswordCheckMixedChars() {
        return Objects.requireNonNullElse(this.reviewerPasswordCheckMixedChars, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatibleSJIS() {
        return Objects.requireNonNullElse(this.compatibleSJIS, false);
    }
}
