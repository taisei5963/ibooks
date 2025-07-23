package com.ibooks.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * 画像ファイルDTO
 */
@Getter
@Builder
public class ImageFileDto {
    /** 画像ファイルパス */
    private String fileName;
    /** 画像URL */
    private String url;
    /** シーケンス番号（サブディレクトリとして使用） */
    @JsonProperty("dir")
    private String sequence;
}
