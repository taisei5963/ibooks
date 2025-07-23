package com.ibooks.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * ファイルユーティリティ
 */
public class FileUtil {

    /**
     * UTF-8 で BOM 付きの場合は除去した BufferedReader を返却する
     * @param filePath ファイルパス
     * @param charset 文字コード
     * @return BufferedReader
     * @throws IOException
     */
    public static BufferedReader newBufferedReader(Path filePath, Charset charset)
            throws IOException {
        BufferedReader reader = java.nio.file.Files.newBufferedReader(filePath, charset);
        if (!charset.equals(StandardCharsets.UTF_8)) {
            return reader;
        }
        char[] c = new char[1];
        int i = reader.read(c, 0, 1);
        if (i==1 && c[0] == '\uFEFF') {
            return reader;
        }
        reader.close();
        return java.nio.file.Files.newBufferedReader(filePath, charset);
    }
}
