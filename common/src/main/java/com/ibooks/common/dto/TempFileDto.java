package com.ibooks.common.dto;

import lombok.Getter;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 一時ファイルDTO
 */
public class TempFileDto {
    @Getter
    private final Path tempFile;
    /** 一時画像リスト */
    @Getter
    private final List<Path> tempImages;
    /** エラーメッセージ */
    @Getter
    private final String error;

    /**
     * コンストラクタ
     *
     * @param tempFile 一時ファイル
     */
    public TempFileDto(Path tempFile) {
        this.tempFile = tempFile;
        this.tempImages = Collections.emptyList();
        this.error = null;
    }

    /**
     * コンストラクタ
     *
     * @param tempFile   一時ファイル
     * @param tempImages 一時画像リスト
     * @param error      エラーメッセージ
     */
    public TempFileDto(Path tempFile, List<Path> tempImages, String error) {
        this.tempFile = tempFile;
        this.tempImages = Objects.isNull(tempImages) ? Collections.emptyList() : tempImages;
        this.error = error;
    }
}
