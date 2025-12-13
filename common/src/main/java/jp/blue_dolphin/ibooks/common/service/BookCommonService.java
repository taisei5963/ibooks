package jp.blue_dolphin.ibooks.common.service;

import jp.blue_dolphin.ibooks.common.config.BooksImageConfig;
import jp.blue_dolphin.ibooks.common.csv.BookForeignKeyCsv;
import jp.blue_dolphin.ibooks.common.database.repository.BookRepository;
import jp.blue_dolphin.ibooks.common.database.repository.CategoryBookRepository;
import jp.blue_dolphin.ibooks.common.database.repository.CategoryRepository;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.model.SearchCategoryBookModel;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * ブック共通サービス
 */
@AllArgsConstructor
@Service
public class BookCommonService {
    /** ブックリポジトリ */
    private BookRepository bookRepository;
    /** カテゴリ×ブックリポジトリ */
    private CategoryBookRepository categoryBookRepository;
    /** ブック画像設定 */
    private BooksImageConfig booksImageConfig;

    /**
     * 引数のブックIDでブックを取得する
     *
     * @param bookId ブックID
     * @return ブックモデル
     */
    public Optional<BookModel> selectById(Long bookId) {
        BookModel model = bookRepository.selectById(bookId).orElse(null);
        return addExtraInfo(model);
    }

    /**
     * 引数のブックモデルに表示するための情報を追加する
     *
     * @param model ブックモデル
     * @return ブックモデル
     */
    public Optional<BookModel> addExtraInfo(BookModel model) {
        if (Objects.isNull(model)) {
            return Optional.empty();
        }
        List<SearchCategoryBookModel> categories =
                categoryBookRepository.selectByBookId(model.getBookId());
        for (int i = 0; i < categories.size(); i++) {
            SearchCategoryBookModel category = categories.get(i);
            switch (i) {
                case 0:
                    model.setCategoryModel1(category);
                    model.setCategoryCode1(category.getCategoryCode());
                    model.setCategoryName1(category.getCategoryName());
                    break;
                case 1:
                    model.setCategoryModel2(category);
                    model.setCategoryCode2(category.getCategoryCode());
                    model.setCategoryName2(category.getCategoryName());
                    break;
                case 2:
                    model.setCategoryModel3(category);
                    model.setCategoryCode3(category.getCategoryCode());
                    model.setCategoryName3(category.getCategoryName());
                    break;
            }
        }
        if (!Strings.isEmpty(model.getPicFileName())) {
            model.setPicFileUrl(
                    booksImageConfig.getBooksImgUrl(model.getBookId(), model.getPicFileName()));
        }
        return Optional.of(model);
    }

    /**
     * カテゴリIDをコードに変換してCSVに設定する
     *
     * @param list ブックCSVリスト
     */
    public <T extends BookForeignKeyCsv> void convertIdToCode(List<T> list) {
        if (list.isEmpty()) {
            return;
        }
        Map<Long, List<SearchCategoryBookModel>> categoriesMap = new HashMap<>();
        {
            List<Long> bookIds = list.stream().map(
                    BookForeignKeyCsv::getBookId).filter(Objects::nonNull).toList();
            List<SearchCategoryBookModel> categories =
                    categoryBookRepository.selectByBookIds(bookIds);
            for (SearchCategoryBookModel category : categories) {
                categoriesMap.computeIfAbsent(category.getBookId(),
                        key -> new ArrayList<>()).add(category);
            }
        }

        for (BookForeignKeyCsv csv : list) {
            List<SearchCategoryBookModel> categories = categoriesMap.get(csv.getBookId());
            if (Objects.nonNull(categories)) {
                for (int i = 0; i < categories.size(); i++) {
                    SearchCategoryBookModel category = categories.get(i);
                    switch (i) {
                        case 0:
                            csv.setCategoryCode1(category.getCategoryCode());
                            break;
                        case 1:
                            csv.setCategoryCode2(category.getCategoryCode());
                            break;
                        case 2:
                            csv.setCategoryCode3(category.getCategoryCode());
                            break;
                    }
                }
            }
        }
    }
}
