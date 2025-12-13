package jp.blue_dolphin.ibooks.common.dto;

import jp.blue_dolphin.ibooks.common.exception.SystemException;
import jp.blue_dolphin.ibooks.common.util.Numbers;
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
 *
 * @param <Z>
 */
public class CsvDto<Z> {
    /** ファイルパス */
    private final Path filePath;

    /** CSV 行データ */
    @Getter
    @Setter
    private List<Z> rows;

    /** エラーメッセージ */
    private final List<String> errors;

    /** 警告メッセージ */
    private final List<String> warns;

    /** 処理件数 */
    @Getter
    private final int rowCount;

    /** 登録件数 */
    @Getter
    private int importCount;

    /**
     * コンストラクタ
     *
     * @param filePath ファイルパス
     * @param rows     CSV 行データ
     * @param errors   エラーメッセージ
     * @param rowCount 処理件数
     */
    public CsvDto(Path filePath, List<Z> rows, List<String> errors, int rowCount) {
        this.filePath = filePath;
        this.rows = rows;
        this.rowCount = rowCount;
        this.errors = errors == null ? new ArrayList<>() : errors;
        this.warns = new ArrayList<>();
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
     * @param errorMessages エラーメッセージ
     */
    public void appendErrorMessage(String errorMessages) {
        if (Objects.isNull(errorMessages)) {
            return;
        }
        this.errors.add(errorMessages);
    }

    /**
     * エラーメッセージを取得する
     *
     * @return エラーメッセージ
     */
    public List<String> getErrorMessages() {
        this.errors.sort((s1, s2) -> {
            Integer i1 = getRowNumFromMessage(s1);
            Integer i2 = getRowNumFromMessage(s2);
            return i1.compareTo(i2);
        });
        return this.errors;
    }

    /**
     * メッセージから行数を取得する<br>
     * 取得できない場合は最大値を返却する
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
     * 警告メッセージ
     *
     * @param warnMsg 警告メッセージ
     */
    public void appendWarnMessages(String warnMsg) {
        if (Objects.isNull(warnMsg)) {
            return;
        }
        this.warns.add(warnMsg);
    }

    /**
     * 警告メッセージを取得する
     *
     * @return 警告メッセージ
     */
    public List<String> getWarnMessages() {
        this.warns.sort((s1, s2) -> {
            Integer i1 = getRowNumFromMessage(s1);
            Integer i2 = getRowNumFromMessage(s2);
            return i1.compareTo(i2);
        });
        return this.warns;
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
     * ファイルパスを返却する
     *
     * @return ファイルパス
     */
    public String getFilePath() {
        if (this.filePath == null) {
            return "";
        }
        return this.filePath.toString();
    }

    /**
     * ファイル名を取得する
     *
     * @return ファイル名
     */
    public String getFileName() {
        if (this.filePath == null) {
            return "";
        }
        return this.filePath.getFileName().toString();
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
     * CSV にエラーがある場合は true を返却する
     *
     * @return {@code true} エラーがある
     */
    public boolean hasError() {
        return !this.errors.isEmpty();
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