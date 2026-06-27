package jp.blue_dolphin.ibooks.common.database.entity;

import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * ブック（検索用）
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
public class SearchCategoryBook {
    /** ブックID */
    @Column(name = "book_id")
    public final Long bookId;

    /** カテゴリID1 */
    @Column(name = "category_id_1")
    public final Long categoryId1;

    /** カテゴリID2 */
    @Column(name = "category_id_2")
    public final Long categoryId2;

    /** カテゴリID3 */
    @Column(name = "category_id_3")
    public final Long categoryId3;

    /** カテゴリコード */
    @Column(name = "category_code")
    public final String categoryCode;

    /** カテゴリ名 */
    @Column(name = "category_name")
    public final String categoryName;
}
