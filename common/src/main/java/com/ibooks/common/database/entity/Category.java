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
 * カテゴリ
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "category")
public class Category {

    /** カテゴリID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    public final Long categoryId;

    /** カテゴリコード. */
    @Column(name = "category_code")
    public final String categoryCode;

    /** カテゴリ名. */
    @Column(name = "category_name")
    public final String categoryName;

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
