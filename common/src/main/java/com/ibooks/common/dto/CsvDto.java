package com.ibooks.common.dto;

import com.ibooks.common.exception.SystemException;
import com.ibooks.common.util.Numbers;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CSV DTO
 */
public class CsvDto<Z> {
    /** ファイルパス */
    private final Path filePath;
    /** CSV行データ */
    @Getter
    @Setter
    private List<Z> rows;
    /** エラーメッセージ */
    private List<String> errorMessages;
    /** 警告メッセージ */
    private List<String> warnMessages;
    /** 処理件数 */
    @Getter
    private final int rowCount;
    /** 登録件数 */
    @Getter
    private int importCount;

    /**
     * コンストラクタ
     * @param filePath 一時ファイルパス
     * @param rows CSV行データ
     * @param errorMessages エラーメッセージリスト
     * @param rowCount 処理件数
     */
    public CsvDto(Path filePath, List<Z> rows, List<String> errorMessages, int rowCount) {
        this.filePath = filePath;
        this.rows = rows;
        this.rowCount = rowCount;
        this.errorMessages = errorMessages == null ? new ArrayList<>() : errorMessages;
        this.warnMessages = new ArrayList<>();
        this.importCount = 0;
    }

    /**
     * 警告とエラーメッセージを返却する
     *
     * @return 警告とエラーメッセージ
     */
    public List<String> getAllMessages() {
        List<String> messages = new ArrayList<>();
        List<String> warns = this.getWarnMessages();
        if (!warns.isEmpty()) {
            messages.addAll(warns);
        }
        List<String> errors = this.getErrorMessages();
        if (!errors.isEmpty()) {
            messages.addAll(errors);
        }
        return messages;
    }

    /**
     * エラーメッセージを追加する
     *
     * @param errorMessage エラーメッセージ
     */
    public void appendErrorMessage(String errorMessage) {
        if (Objects.isNull(errorMessage)) {
            return;
        }
        this.errorMessages.add(errorMessage);
    }

    /**
     * エラーメッセージを取得する
     *
     * @return エラーメッセージ
     */
    public List<String> getErrorMessages() {
        this.errorMessages.sort((s1, s2) -> {
            Integer i1 = getRowNumFromMessage(s1);
            Integer i2 = getRowNumFromMessage(s2);
            return i1.compareTo(i2);
        });
        return this.errorMessages;
    }

    /**
     * メッセージから行数を取得する
     *
     * @param message メッセージ
     * @return 行数
     */
    private static Integer getRowNumFromMessage(String message) {
        int index = message.indexOf("行");
        if (index != -1) {
            return Numbers.toInt(message.substring(0, index));
        }
        return Integer.MAX_VALUE;
    }

    /**
     * 警告メッセージを追加する
     *
     * @param warnMessage 警告メッセージ
     */
    public void appendWarnMessage(String warnMessage) {
        if (Objects.isNull(warnMessage)) {
            return;
        }
        this.warnMessages.add(warnMessage);
    }

    /**
     * 警告メッセージを取得する
     *
     * @return 警告メッセージ
     */
    public List<String> getWarnMessages() {
        this.warnMessages.sort((s1, s2) -> {
            Integer i1 = getRowNumFromMessage(s1);
            Integer i2 = getRowNumFromMessage(s2);
            return i1.compareTo(i2);
        });
        return this.warnMessages;
    }

    /**
     * 登録処理成功<br>
     * 登録件数をカウントアップする
     */
    public void importSuccess() {
        this.importCount += 1;
    }

    /**
     * 登録処理成功<br>
     * 登録件数をカウントアップする
     *
     * @param successCount 成功件数
     */
    public void importSuccess(int successCount) {
        this.importCount += successCount;
    }

    /**
     * ファイルパスを取得する
     *
     * @return ファイルパス
     */
    public String getFilePath() {
        if (this.filePath == null) {
            return "";
        }
        return filePath.toString();
    }

    /**
     * ファイル名を取得する
     *
     * @return ファイルパス
     */
    public String getFileName() {
        if (this.filePath == null) {
            return "";
        }
        return filePath.getFileName().toString();
    }

    /**
     * CSV 行リストが空の場合は true を返却する
     *
     * @return {@code true} 行リストが空
     */
    public boolean isEmpty() {
        if (this.rows == null) {
            return true;
        }
        return this.rows.isEmpty();
    }

    /**
     * CSV 二エラーがある場合は true を返却する
     *
     * @return {@code true} エラーがある
     */
    public boolean hasError() {
        return !this.errorMessages.isEmpty();
    }

    /**
     * ファイルサイズを返却する
     *
     * @return ファイルサイズ
     */
    public int fileSize() {
        if (this.filePath == null) {
            return 0;
        }
        if (Files.exists(this.filePath)) {
            try {
                return (int) Files.size(this.filePath);
            } catch (IOException e) {
                throw new SystemException(
                        "クライアントからアップロードされたファイルの読み込みに失敗", e);
            }
        }
        return 0;
    }
}
