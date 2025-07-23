package com.ibooks.common.database.repository;

import com.ibooks.common.constants.SequenceType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * シーケンスレポジトリ
 */
@Repository
public interface SequenceManagerRepository {
    Integer next(SequenceType sequenceType, LocalDateTime now);
}
