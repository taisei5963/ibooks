package jp.blue_dolphin.ibooks.common.dto;

import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.model.CategoryModel;
import jp.blue_dolphin.ibooks.common.model.ReviewModel;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * モデルキャッシュ
 */
public class ModelCache {
    /** ブックキャッシュ（IDがキー） */
    private final Map<Long, BookModel> idBooks;
    /** ブックキャッシュ（コードがキー） */
    private final Map<String, BookModel> books;
    /** レビューキャッシュ */
    private final Map<Long, List<ReviewModel>> reviews;
    /** カテゴリモデルキャッシュ */
    @Setter
    private Map<Long, CategoryModel> categories;

    /**
     * コンストラクタ
     */
    public ModelCache() {
        this.idBooks = new HashMap<>();
        this.books = new HashMap<>();
        this.reviews = new HashMap<>();
    }

    /**
     * キャッシュからブックを取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    public Optional<BookModel> getBooks(Long bookId) {
        return Optional.ofNullable(this.idBooks.get(bookId));
    }

    /**
     * キャッシュからブックを取得する
     *
     * @param janCode JANコード
     * @return ブックモデル
     */
    public Optional<BookModel> getBook(String janCode) {
        return Optional.ofNullable(this.books.get(janCode));
    }

    /**
     * キャッシュにブックを追加する
     *
     * @param book ブックモデル
     */
    public void putBook(BookModel book) {
        if (Objects.isNull(book)) {
            return;
        }
        this.idBooks.put(book.getBookId(), book);
        this.books.put(book.getJanCode(), book);
    }

    /**
     * カテゴリがキャッシュに設定されている場合は true を返却する
     *
     * @return {@code true} カテゴリが設定済
     */
    public boolean isSetCategories() {
        return this.categories != null;
    }

    /**
     * キャッシュからカテゴリを取得する
     *
     * @param categoryId カテゴリID
     * @return カテゴリモデル
     */
    public Optional<CategoryModel> getCategory(Long categoryId) {
        return Optional.ofNullable(this.categories.get(categoryId));
    }

    /**
     * キャッシュからレビューを取得する
     *
     * @param reviewId レビューID
     * @return レビューモデル
     */
    public List<ReviewModel> getReview(Long reviewId) {
        if (Objects.isNull(reviewId)) {
            return null;
        }
        return this.reviews.get(reviewId);
    }

    /**
     * キャッシュにレビューを追加する
     *
     * @param reviewId レビューID
     * @param reviews  レビューモデルリスト
     */
    public void putReview(Long reviewId, List<ReviewModel> reviews) {
        this.reviews.put(reviewId, reviews);
    }
}
