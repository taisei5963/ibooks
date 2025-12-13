package jp.blue_dolphin.ibooks.common.dto;

import lombok.Getter;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * 一時ファイルDTO
 */
public class TempFileDto {
    /** 一時ファイル */
    @Getter
    private final Path tmpFile;

    /** 一時画像リスト */
    @Getter
    private final List<Path> tmpImages;

    /** エラーメッセージ */
    @Getter
    private final String error;

    /**
     * コンストラクタ
     *
     * @param tmpFile 一時ファイル
     */
    public TempFileDto(Path tmpFile) {
        this.tmpFile = tmpFile;
        this.tmpImages = Collections.emptyList();
        this.error = null;
    }

    /**
     * コンストラクタ
     *
     * @param tmpFile   一時ファイル
     * @param tmpImages 一時画像リスト
     * @param error     エラーメッセージ
     */
    public TempFileDto(Path tmpFile, List<Path> tmpImages, String error) {
        this.tmpFile = tmpFile;
        this.tmpImages = tmpImages;
        this.error = error;
    }
}
