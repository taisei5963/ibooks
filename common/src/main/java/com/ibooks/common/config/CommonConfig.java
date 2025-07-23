package com.ibooks.common.config;

/**
 * システム共通設定
 */
public interface CommonConfig {
    /**
     * システムで扱う文字コードが Windows-31J 互換である場合は true を返却する。
     *
     * @return {@code true} Windows-31J 互換
     */
    boolean isCompatibleSJIS();
}
