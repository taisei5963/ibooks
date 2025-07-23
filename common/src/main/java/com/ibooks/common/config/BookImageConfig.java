package com.ibooks.common.config;

import com.ibooks.common.util.Strings;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * ブック画像ファイルURLインタフェース
 */
public interface BookImageConfig extends ImageConfig {

    /**
     * ディレクトリ階層を取得する
     *
     * @return ディレクトリの階層
     */
    Integer getDirHierarchy();

    /**
     * 階層化されたブック画像ディレクトリパスを取得する
     *
     * @param bookId ブックID
     * @return ブック画像ディレクトリパス
     */
    default Optional<Path> getHierarchyImgDir(Long bookId) {
        if (Objects.isNull(bookId)) {
            return Optional.empty();
        }
        int dirHierarchy = getComputedDirHierarchy();
        String base = convertHierarchy(bookId);
        Path path;
        if (dirHierarchy == 0) {
            path = Path.of(bookId.toString());
        } else if (dirHierarchy == 1) {
            path = Path.of(base.substring(2), bookId.toString());
        } else {
            path = Path.of(base.substring(0, 2), base.substring(2), bookId.toString());
        }
        return Optional.of(path);
    }

    /**
     * 引数のブック画像URLを取得する
     *
     * @param bookId      ブック画像
     * @param imgFileName 画像ファイル名
     * @return ブック画像URL
     */
    default String getBookImgUrl(Long bookId, String imgFileName) {
        if (Strings.isEmpty(imgFileName)) {
            return "";
        }
        int dirHierarchy = getComputedDirHierarchy();
        String base = convertHierarchy(bookId);
        StringJoiner sj = new StringJoiner("/");
        sj.add(getBaseUrl());
        if (dirHierarchy == 1) {
            sj.add(base.substring(2));
        } else if (dirHierarchy == 2) {
            sj.add(base.substring(0, 2));
            sj.add(base.substring(2));
        }
        sj.add(bookId.toString());
        sj.add(imgFileName);
        return sj.toString();
    }

    /**
     * 補正したディレクトリ階層を取得する<br>
     * 0 より小さい場合 0 を返却する<br>
     * 2 より大きい場合 2 を返却する<br>
     *
     * @return ディレクトリ階層
     */
    private int getComputedDirHierarchy() {
        int dirHierarchy = getDirHierarchy() != null ? getDirHierarchy() : 0;
        if (dirHierarchy > 2) {
            dirHierarchy = 2;
        } else if (dirHierarchy < 0) {
            dirHierarchy = 0;
        }
        return dirHierarchy;
    }

    /**
     * 引数のブックIDを階層ディレクトリのベース文字列に変換する<br>
     * 例１：ブックIDが 123456 の場合は 3456 を返却する<br>
     * 例２：ブックIDが 1 の場合は 0001 を返却する<br>
     *
     * @param bookId ブックID
     * @return 階層ディレクトリのベース文字列
     */
    private static String convertHierarchy(Long bookId) {
        String dir = String.format("%04d", bookId);
        if (dir.length() > 4) {
            dir = dir.substring(dir.length() - 4);
        }
        return dir;
    }
}
