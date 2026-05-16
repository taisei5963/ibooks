package jp.blue_dolphin.ibooks.common.config;

import jp.blue_dolphin.ibooks.common.util.Strings;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 画像設定インタフェース
 */
public interface ImageConfig {
    /**
     * ベースURLを返却する
     *
     * @return ベースURL
     */
    String getBaseUrl();

    /**
     * 一時ベースURLを返却する
     *
     * @return 一時ベースURL
     */
    String getTempBaseUrl();

    /**
     * アップロード先ディレクトリを返却する
     *
     * @return アップロード先ディレクトリ
     */
    Optional<Path> getUploadDir();

    /**
     * 一時アップロード先ディレクトリを返却する
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
    default String getImgUrl(String fileName) {
        return getImgUrl(fileName, null, false);
    }

    /**
     * 画像のURLを取得する
     *
     * @param fileName ファイル名
     * @param dir      ディレクトリ名
     * @return 画像のURL
     */
    default String getImgUrl(String fileName, @Nullable String dir) {
        return getImgUrl(fileName, dir, false);
    }

    /**
     * 一時画像のURLを取得する
     *
     * @param fileName ファイル名
     * @return 画像のURL
     */
    default String getTempImgUrl(String fileName) {
        return getImgUrl(fileName, null, true);
    }

    /**
     * 一時画像のURLを取得する
     *
     * @param fileName ファイル名
     * @param dir      ディレクトリ名
     * @return 画像のURL
     */
    default String getTempImgUrl(String fileName, @Nullable String dir) {
        return getImgUrl(fileName, dir, true);
    }

    /**
     * 画像のURLを取得する
     *
     * @param fileName ファイル名
     * @param dir      ディレクトリ名
     * @param isTemp   {@code true} 一時ファイル
     * @return 画像のURL
     */
    default String getImgUrl(String fileName, @Nullable String dir, boolean isTemp) {
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
