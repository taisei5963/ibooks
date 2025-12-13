package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.csv.BookCsv;
import jp.blue_dolphin.ibooks.common.database.dao.BookDao;
import jp.blue_dolphin.ibooks.common.database.entity.Book;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.util.Dates;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ブックリポジトリ
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
    public Optional<BookModel> selectByJanCode(String janCode) {
        if (Objects.isNull(janCode)) {
            return Optional.empty();
        }
        Book entity = bookDao.selectByJanCode(janCode);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookModel> selectByJanCodes(List<String> janCodes) {
        if (Objects.isNull(janCodes) || janCodes.isEmpty()) {
            return List.of();
        }
        List<Book> entities = bookDao.selectByJanCodes(janCodes);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return List.of();
        }
        List<BookModel> models = new ArrayList<>();
        for (Book entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<BookModel> selectBySearchCond(String title, String author,
                                                            String publisher,
                                                            Long categoryId, String orderBy,
                                                            SelectOptions options) {
        List<Book> entities =
                bookDao.selectBySearchCond(title, author, publisher, categoryId, orderBy, options);
        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<BookModel> models = new ArrayList<>();
        for (Book entity : entities) {
            models.add(convertModel(entity));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<BookCsv> selectBySearchCondForCsv(String title, String author,
                                                          String publisher,
                                                          Long categoryId, String orderBy,
                                                          SelectOptions options) {
        List<Book> entities =
                bookDao.selectBySearchCond(title, author, publisher, categoryId, orderBy, options);
        long count = options.getCount();
        if (count == 0) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<BookCsv> list = new ArrayList<>();
        for (Book entity : entities) {
            list.add(convertCsv(entity));
        }
        return new SearchResult<>(list, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookModel> selectByTitleAndPublisher(String title, String publisher) {
        if (Objects.isNull(title) || Objects.isNull(publisher)) {
            return Optional.empty();
        }
        Book entity = bookDao.selectByTitleAndPublisher(title, publisher);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookModel store(BookModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Book> result;
        if (Objects.isNull(model.getBookId())) {
            BookModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = bookDao.insert(convertEntity(setModel));
        } else {
            BookModel setModel = model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = bookDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * 引数のブックエンティティをブックモデルに変換する
     *
     * @param entity ブックエンティティ
     * @return ブックモデル
     */
    private static BookModel convertModel(Book entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return BookModel.builder()
                .bookId(entity.bookId)
                .janCode(entity.janCode)
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
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のブックモデルをブックエンティティに変換する
     *
     * @param model ブックモデル
     * @return ブックエンティティ
     */
    private static Book convertEntity(BookModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Book.builder()
                .bookId(model.getBookId())
                .janCode(model.getJanCode())
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
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }

    /**
     * ブックエンティティをブックCSVに変換する
     *
     * @param entity ブックエンティティ
     * @return ブックCSV
     */
    private BookCsv convertCsv(Book entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        BookCsv csv = new BookCsv();
        csv.setCsvDataType("");
        csv.setJanCode(entity.janCode);
        csv.setTitle(entity.title);
        csv.setSubTitle(entity.subTitle);
        csv.setAuthor1(entity.author1);
        csv.setAuthor2(entity.author2);
        csv.setTranslator(entity.translator);
        csv.setPublisher(entity.publisher);
        // INFO: 値が設定されている場合は文字列に置き換える
        csv.setPicFileName(Strings.isEmpty(entity.picFileName) ? "" : BookCsv.IMAGE_REGISTERED);
        // INFO: カテゴリコード変換用
        csv.setCategoryId1(entity.categoryId1);
        csv.setCategoryId2(entity.categoryId2);
        csv.setCategoryId3(entity.categoryId3);
        csv.setCreatedAt(Dates.format(entity.createdAt));
        csv.setUpdatedAt(Dates.format(entity.updatedAt));
        csv.setCreatedId(entity.createdId);
        return csv;
    }
}
