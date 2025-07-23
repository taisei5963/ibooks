package com.ibooks.common.dto;

import com.ibooks.common.model.BookModel;
import com.ibooks.common.model.CategoryModel;
import com.ibooks.common.model.ReviewModel;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * モデルキャッシュ
 */
public class ModelCache {
    /** ブックキャッシュ（コードがキー） */
    private final Map<String, BookModel> codeBooks;
    /** ブックキャッシュ（IDがキー） */
    private final Map<Long, BookModel> idBooks;
    /** レビューキャッシュ */
    private final Map<Long, ReviewModel> idReviewers;
    /** カテゴリモデルキャッシュ */
    @Setter
    private Map<Long, CategoryModel> categories;

    /**
     * コンストラクタ
     */
    public ModelCache() {
        this.codeBooks = new HashMap<>();
        this.idBooks = new HashMap<>();
        this.idReviewers = new HashMap<>();
    }

    /**
     * キャッシュからブックを取得する
     *
     * @param bookCode ブックコード
     * @return ブックモデル
     */
    public Optional<BookModel> getBook(String bookCode) {
        return Optional.ofNullable(codeBooks.get(bookCode));
    }

    /**
     * キャッシュからブックを取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    public Optional<BookModel> getBook(Long bookId) {
        return Optional.ofNullable(idBooks.get(bookId));
    }

    /**
     * キャッシュにブックを追加する
     *
     * @param bookModel ブックモデル
     */
    public void putBook(BookModel bookModel) {
        if (Objects.isNull(bookModel)) {
            return;
        }
        this.codeBooks.put(bookModel.getBookCode(), bookModel);
        this.idBooks.put(bookModel.getBookId(), bookModel);
    }

    /**
     * キャッシュからレビューを取得する
     *
     * @param reviewId レビューID
     * @return レビューモデル
     */
    public Optional<ReviewModel> getReview(Long reviewId) {
        return Optional.ofNullable(idReviewers.get(reviewId));
    }

    /**
     * キャッシュにレビューを追加する
     *
     * @param reviewModel ブックモデル
     */
    public void putReview(ReviewModel reviewModel) {
        if (Objects.isNull(reviewModel)) {
            return;
        }
        this.idReviewers.put(reviewModel.getReviewId(), reviewModel);
    }

    /**
     * カテゴリがキャッシュに設定されている場合は true を返却する
     * @return {@code true} カテゴリが設定済
     */
    public boolean isSetCategories() {
        return this.categories != null;
    }

    /**
     * キャッシュからカテゴリを取得する
     * @param categoryId カテゴリID
     * @return カテゴリモデル
     */
    public Optional<CategoryModel> getCategory(Long categoryId) {
        return Optional.ofNullable(this.categories.get(categoryId));
    }
}
