package com.ibooks.common.database.repository;

import com.ibooks.common.constants.SequenceType;
import com.ibooks.common.database.dao.SequenceManagerDao;
import com.ibooks.common.database.entity.SequenceManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * シーケンスリポジトリ
 */
@AllArgsConstructor
@Repository
public class SequenceManagerRepositoryImpl implements SequenceManagerRepository {
    /** シーケンスDAO */
    private SequenceManagerDao sequenceManagerDao;

    /**
     * 次のシーケンス番号を取得する
     *
     * @param sequenceType シーケンスタイプ
     * @param now          現在日時
     * @return 次のシーケンス番号
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer next(SequenceType sequenceType, LocalDateTime now) {
        List<SequenceManager> entites = sequenceManagerDao.selectBySequenceType(sequenceType);
        if (entites.isEmpty()) {
            SequenceManager sequence = SequenceManager.builder()
                    .sequence(1)
                    .sequenceType(sequenceType)
                    .updatedAt(now)
                    .build();
            sequenceManagerDao.insert(sequence);
            return 1;
        }
        SequenceManager entity = entites.getFirst();
        return next(sequenceType, entity.sequenceManagerId, entity.sequence, now);
    }

    /**
     * 次のシーケンス番号を取得する
     *
     * @param sequenceType      シーケンスタイプ
     * @param sequenceManagerId シーケンスマネージャーID
     * @param sequence          現在のシーケンス
     * @param now               現在日時
     * @return 次のシーケンス番号
     */
    private int next(SequenceType sequenceType, Long sequenceManagerId, int sequence, LocalDateTime now) {
        int next = sequence + 1;
        if (sequenceType.getCycle() > 0 && next >= sequenceType.getCycle()) {
            next = 1;
        }
        int count = sequenceManagerDao.updateSequence(sequenceManagerId, next, now, sequence);
        if (count != 1) {
            return next(sequenceType, sequenceManagerId, next, now);
        }
        return next;
    }
}
