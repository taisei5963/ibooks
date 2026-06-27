package jp.blue_dolphin.ibooks.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ファイルユーティリティ
 */
public class FileUtil {
    /**
     * UTF-8 で BOM がついている場合は除去した BufferReader を返却する
     *
     * @param filePath ファイルパス
     * @param charset  文字コード
     * @return Buffer Reader
     * @throws IOException 入出力で例外が発生した場合
     */
    public static BufferedReader newBufferedReader(Path filePath, Charset charset)
            throws IOException {
        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(filePath, charset);
            if (!charset.equals(StandardCharsets.UTF_8)) {
                return reader;
            }
            char[] ch = new char[1];
            int i = reader.read(ch, 0, 1);
            if (i == 1 && ch[0] == '\uFEFF') {
                return reader;
            }
            reader.close();
            return Files.newBufferedReader(filePath, charset);
        } catch (MalformedInputException e) {
            if (charset.equals(StandardCharsets.UTF_8)) {
                // INFO: UTF-8 で読み込めない場合は Windows-31J (SJIS) でリトライする
                return new BufferedReader(new InputStreamReader(Files.newInputStream(filePath),
                        Charset.forName("Windows-31J")));
            }
            throw e;
        }
    }
}
