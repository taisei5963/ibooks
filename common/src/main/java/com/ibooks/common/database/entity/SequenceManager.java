package com.ibooks.common.database.entity;

import com.ibooks.common.constants.SequenceType;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import java.time.LocalDateTime;

/**
 * シーケンスマネージャー
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "sequence_manager")
public class SequenceManager {
    /** シーケンスID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence_manager_id")
    public final Long sequenceManagerId;

    /** シーケンス */
    @Column(name = "sequence")
    public final Integer sequence;

    /** シーケンスタイプ */
    @Column(name = "sequence_type")
    public final SequenceType sequenceType;

    /** 更新日時 */
    @Column(name = "updated_at")
    public final LocalDateTime updatedAt;
}
