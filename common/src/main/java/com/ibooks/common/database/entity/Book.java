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
 * ブック
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "book")
public class Book {
    /** ブックID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    public final Long bookId;

    /** ブックコード. */
    @Column(name = "book_code")
    public final String bookCode;

    /** タイトル. */
    @Column(name = "title")
    public final String title;

    /** サブタイトル. */
    @Column(name = "sub_title")
    public final String subTitle;

    /** 作者１. */
    @Column(name = "author1")
    public final String author1;

    /** 作者２. */
    @Column(name = "author2")
    public final String author2;

    /** 翻訳者. */
    @Column(name = "translator")
    public final String translator;

    /** 出版社. */
    @Column(name = "publisher")
    public final String publisher;

    /** 画像ファイル名. */
    @Column(name = "pic_file_name")
    public final String picFileName;

    /** 総評価. */
    @Column(name = "total_rating")
    public final Double totalRating;

    /** カテゴリID１. */
    @Column(name = "category_id_1")
    public final Long categoryId1;

    /** カテゴリID２. */
    @Column(name = "category_id_2")
    public final Long categoryId2;

    /** カテゴリID３. */
    @Column(name = "category_id_3")
    public final Long categoryId3;

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
