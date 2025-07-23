package com.ibooks.common.config;

import com.ibooks.common.util.Strings;
import jakarta.annotation.Nullable;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 画像設定インタフェース
 */
public interface ImageConfig {
    /**
     * ベースURLを取得する
     *
     * @return ベースURL
     */
    String getBaseUrl();

    /**
     * 一時ベースURLを取得する
     *
     * @return 一時ベースURL
     */
    String getTempBaseUrl();

    /**
     * アップロード先ディレクトリを取得する
     *
     * @return アップロード先ディレクトリ
     */
    Optional<Path> getUploadDir();

    /**
     * 一時アップロード先ディレクトリを取得する
     *
     * @return 一時アップロード先ディレクトリ
     */
    Optional<Path> getTempUploadDir();

    /**
     * 画像のURLを取得する
     *
     * @param fileName ファイル名
     * @return 画像のURL
     */
    default String getImageUrl(String fileName) {
        return getImageUrl(fileName, null, false);
    }

    default String getImageUrl(String fileName, @Nullable String dir) {
        return getImageUrl(fileName, dir, false);
    }

    /**
     * 一時画像のURLを取得する
     *
     * @param fileName ファイル名
     * @return 画像のURL
     */
    default String getTempImageUrl(String fileName) {
        return getImageUrl(fileName, null, true);
    }

    /**
     * 一時画像のURLを取得する
     *
     * @param fileName ファイル名
     * @param dir      ディレクトリ
     * @return 画像のURL
     */
    default String getTempImageUrl(String fileName, @Nullable String dir) {
        return getImageUrl(fileName, dir, true);
    }

    /**
     * 画像のURLを取得する
     * @param fileName ファイル名
     * @param dir ディレクトリ
     * @param isTemp 一時画像かどうか
     * @return 画像のURL
     */
    default String getImageUrl(String fileName, @Nullable String dir, boolean isTemp) {
        if (Strings.isBlank(fileName)) {
            return "";
        }
        String baseUrl = isTemp ? getTempBaseUrl() : getBaseUrl();
        StringBuilder sb = new StringBuilder(baseUrl);
        if (!baseUrl.endsWith("/")) {
            sb.append("/");
        }
        if (!Strings.isBlank(dir)) {
            sb.append(dir);
            if (!dir.endsWith("/")) {
                sb.append("/");
            }
        }
        sb.append(fileName);
        return sb.toString();
    }
}
