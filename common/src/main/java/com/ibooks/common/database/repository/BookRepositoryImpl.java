package com.ibooks.common.database.repository;

import com.ibooks.common.csv.BookCsv;
import com.ibooks.common.database.dao.BookDao;
import com.ibooks.common.database.entity.Book;
import com.ibooks.common.database.entity.Review;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.BookModel;
import com.ibooks.common.model.ReviewModel;
import com.ibooks.common.util.Dates;
import com.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ブックレポジトリ
 */
@AllArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    /** ブックDAO */
    private BookDao bookDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookModel> selectById(Long bookId) {
        if (Objects.isNull(bookId)) {
            return Optional.empty();
        }
        Book entity = bookDao.selectById(bookId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookModel> selectByCode(String bookCode) {
        if (Strings.isEmpty(bookCode)) {
            return Optional.empty();
        }
        Book entity = bookDao.selectByCode(bookCode);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<BookCsv> selectBySearchCond(String bookCode, String title, String author, String publisher,
                                                    Long categoryId, Long userId, String orderBy,
                                                    SelectOptions options) {
        List<Book> entities = bookDao.selectBySearchCond(bookCode, title, author, publisher, categoryId, userId,
                orderBy, options);
        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<BookCsv> csvList = new ArrayList<>();
        for (Book entity : entities) {
            csvList.add(convertCsv(entity));
        }
        return new SearchResult<>(csvList, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookModel> selectByTitleAndPublisher(String title, String publisher) {
        if (Strings.isEmpty(title) || Strings.isEmpty(publisher)) {
            return Optional.empty();
        }
        Book entity = bookDao.selectByTitleAndPublisher(title, publisher);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookModel store(BookModel model, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Book> result;
        if (Objects.isNull(model.getBookId())) {
            BookModel setModel = model.toBuilder().createDate(now).updateDate(now).createId(registerId).ver(1).build();
            result = bookDao.insert(convertEntity(setModel));
        } else {
            BookModel setModel = model.toBuilder().updateDate(now).createId(registerId).build();
            result = bookDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int avgRatingByBookId(Long bookId) {
        if (Objects.isNull(bookId)) {
            return 0;
        }
        return bookDao.avgRatingById(bookId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(BookModel model) {
        if (Objects.isNull(model)) {
            return 0;
        }
        Result<Book> result = bookDao.delete(convertEntity(model));
        return result.getCount();
    }

    /**
     * ブックエンティティをブックモデルに変換する
     *
     * @param entity ブックエンティティ
     * @return ブックモデル
     */
    private BookModel convertModel(Book entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return BookModel.builder()
                .bookId(entity.bookId)
                .bookCode(entity.bookCode)
                .title(entity.title)
                .subTitle(entity.subTitle)
                .author1(entity.author1)
                .author2(entity.author2)
                .translator(entity.translator)
                .publisher(entity.publisher)
                .picFileName(entity.picFileName)
                .totalRating(entity.totalRating)
                .categoryId1(entity.categoryId1)
                .categoryId2(entity.categoryId2)
                .categoryId3(entity.categoryId3)
                .createDate(entity.createdAt)
                .updateDate(entity.updatedAt)
                .createId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * ブックモデルをブックエンティティに変換する
     *
     * @param model ブックモデル
     * @return ブックエンティティ
     */
    private Book convertEntity(BookModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Book.builder()
                .bookId(model.getBookId())
                .bookCode(model.getBookCode())
                .title(model.getTitle())
                .subTitle(model.getSubTitle())
                .author1(model.getAuthor1())
                .author2(model.getAuthor2())
                .translator(model.getTranslator())
                .publisher(model.getPublisher())
                .picFileName(model.getPicFileName())
                .totalRating(model.getTotalRating())
                .categoryId1(model.getCategoryId1())
                .categoryId2(model.getCategoryId2())
                .categoryId3(model.getCategoryId3())
                .createdAt(model.getCreateDate())
                .updatedAt(model.getUpdateDate())
                .createdId(model.getCreateId())
                .ver(model.getVer())
                .build();
    }

    private BookCsv convertCsv(Book entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        BookCsv csv = new BookCsv();
        csv.setCsvDataType("");
        csv.setBookId(entity.bookId);
        csv.setBookCode(entity.bookCode);
        csv.setTitle(entity.title);
        csv.setSubTitle(entity.subTitle);
        csv.setAuthor1(entity.author1);
        csv.setAuthor2(entity.author2);
        csv.setTranslator(entity.translator);
        csv.setPublisher(entity.publisher);
        csv.setPicFileName(Strings.isEmpty(entity.picFileName) ? "" : BookCsv.IMAGE_REGISTERED);
        csv.setTotalRating(entity.totalRating);
        // INFO: カテゴリコード変換用
        csv.setCategoryId1(entity.categoryId1);
        csv.setCategoryId2(entity.categoryId2);
        csv.setCategoryId3(entity.categoryId3);
        csv.setCreateDate(Dates.format(entity.createdAt));
        csv.setUpdateDate(Dates.format(entity.updatedAt));
        csv.setCreateId(entity.createdId);
        return csv;
    }
}
