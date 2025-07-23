package com.ibooks.common.service;

import com.ibooks.common.constants.SequenceType;
import com.ibooks.common.database.repository.SequenceManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * シーケンスマネージャーサービス
 */
@AllArgsConstructor
@Service
public class SequenceManagerService {
    /** シーケンスリポジトリ */
    private SequenceManagerRepository sequenceManagerRepository;

    /**
     * シーケンス番号を採番し返却する
     *
     * @param type シーケンスタイプ
     * @return シーケンス番号
     */
    public Integer nextSeq(SequenceType type) {
        return nextSeq(type, LocalDateTime.now());
    }

    /**
     * シーケンス番号を採番し返却する
     *
     * @param type シーケンスタイプ
     * @param now  現在日時
     * @return シーケンス番号
     */
    public Integer nextSeq(SequenceType type, LocalDateTime now) {
        return sequenceManagerRepository.next(type, now);
    }

    /**
     * シーケンス番号を採番しDBに保存する形式（プレフィックスと現在日時を追加）で返却する
     *
     * @param type シーケンスタイプ
     * @return シーケンス文字列
     */
    public String createSequenceIdForDB(SequenceType type) {
        LocalDateTime now = LocalDateTime.now();
        int seq = nextSeq(type, now);
        return type.getPrefix() + now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss")) +
                String.format("%04d", seq);
    }

    /**
     * シーケンス番号を採番しDBに保存する形式（プレフィックスと現在日時を追加）で返却する
     *
     * @param type シーケンスタイプ
     * @return シーケンス文字列
     */
    public String createSequenceCodeForDB(SequenceType type) {
        LocalDateTime now = LocalDateTime.now();
        int seq = nextSeq(type, now);
        return type.getPrefix() + now.format(DateTimeFormatter.ofPattern("uuMM")) +
                String.format("%05d", seq);
    }

    /**
     * シーケンス番号を裁判し、プレフィックスを追加して文字列として返却する
     *
     * @param type シーケンスタイプ
     * @param pad  指定桁数を 0 パディング
     * @return シーケンス文字列
     */
    public String createSequenceString(SequenceType type, int pad) {
        int seq = nextSeq(type);
        if (pad < 1) {
            return type.getPrefix() + seq;
        }
        return type.getPrefix() + String.format("%0" + pad + "d" + seq);
    }
}
