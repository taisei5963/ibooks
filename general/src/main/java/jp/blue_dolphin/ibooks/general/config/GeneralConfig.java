package jp.blue_dolphin.ibooks.general.config;

import jp.blue_dolphin.ibooks.common.config.CommonConfig;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.Objects;

/**
 * 一般サイト用設定
 */
@Component
public class GeneralConfig implements CommonConfig {
    /** Windows-31J 互換かどうか */
    @Setter
    @Value("${general-config.compatible-sjis}")
    private Boolean compatibleSJIS;

    /** 丸めモード */
    @Setter
    @Value("${general-config.rating.rounding-mode}")
    private RoundingMode roundingMode;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatibleSJIS() {
        return Objects.requireNonNullElse(this.compatibleSJIS, false);
    }
}
