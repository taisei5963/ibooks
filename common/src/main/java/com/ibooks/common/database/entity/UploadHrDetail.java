package com.ibooks.common.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

import java.time.LocalDateTime;

/**
 * アップロード履歴詳細
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "upload_hr_detail")
public class UploadHrDetail {

    /** アップロード履歴詳細ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_hr_detail_id")
    public final Long uploadHrDetailId;

    /** アップロード履歴ID */
    @Column(name = "upload_hr_id")
    public final Long uploadHrId;

    /** ログレベル */
    @Column(name = "log_level")
    public final String logLevel;

    /** 内容 */
    @Column(name = "content")
    public final String content;

    /** 作成日. */
    @Column(name = "created_at")
    public final LocalDateTime createdAt;

    /** 更新日. */
    @Column(name = "updated_at")
    public final LocalDateTime updatedAt;

    /** 削除日. */
    @Column(name = "deleted_at")
    public final LocalDateTime deletedAt;

    /** 作成者ID. */
    @Column(name = "created_id")
    public final String createdId;

    /** バージョン. */
    @Version
    @Column(name = "ver")
    public final Integer ver;
}
