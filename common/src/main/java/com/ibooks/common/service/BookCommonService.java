package com.ibooks.common.service;

import com.ibooks.common.config.BookImageConfig;
import com.ibooks.common.csv.BookForeignKeyCsv;
import com.ibooks.common.database.repository.BookRepository;
import com.ibooks.common.database.repository.CategoryRepository;
import com.ibooks.common.model.BookModel;
import com.ibooks.common.model.CategoryModel;
import com.ibooks.common.util.Strings;
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
    /** カテゴリリポジトリ */
    private CategoryRepository categoryRepository;
    /** ブック画像設定 */
    private BookImageConfig bookImageConfig;

    /**
     * 引数のブックIDのブック情報を取得する
     *
     * @param bookId 　ブックID
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
        CategoryModel category1 = getCategoryByIdOrNull(model.getCategoryId1());
        CategoryModel category2 = getCategoryByIdOrNull(model.getCategoryId2());
        CategoryModel category3 = getCategoryByIdOrNull(model.getCategoryId3());

        if (!Objects.isNull(category1)) {
            model.setCategoryName1(category1.getCategoryName());
            model.setCategoryModel1(category1);
        }
        if (!Objects.isNull(category2)) {
            model.setCategoryName1(category2.getCategoryName());
            model.setCategoryModel2(category2);
        }
        if (!Objects.isNull(category3)) {
            model.setCategoryName1(category3.getCategoryName());
            model.setCategoryModel3(category3);
        }

        if (!Strings.isEmpty(model.getImgFileUrl())) {
            model.setImgFileUrl(bookImageConfig.getBookImgUrl(model.getBookId(), model.getPicFileName()));
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
        Map<Long, List<CategoryModel>> categoryMap = new HashMap<>();
        {
            List<Long> bookIds = list.stream().map(BookForeignKeyCsv::getBookId).filter(Objects::nonNull).toList();
            List<CategoryModel> categories = categoryRepository.selectByBookIds(bookIds);
            for (CategoryModel category : categories) {
                categoryMap.computeIfAbsent(category.getCategoryId(), key -> new ArrayList<>()).add(category);
            }
        }

        for (BookForeignKeyCsv csv : list) {
            List<CategoryModel> categories = categoryMap.get(csv.getBookId());
            if (!Objects.isNull(categories)) {
                for (int i = 0; i < categories.size(); i++) {
                    CategoryModel categoryModel = categories.get(i);
                    switch (i) {
                        case 0:
                            csv.setCategoryCode1(categoryModel.getCategoryCode());
                            break;
                        case 1:
                            csv.setCategoryCode2(categoryModel.getCategoryCode());
                            break;
                        case 2:
                            csv.setCategoryCode3(categoryModel.getCategoryCode());
                            break;
                    }
                }
            }
        }
    }

    /**
     * 引数のカテゴリID からカテゴリモデルを取得する
     *
     * @param categoryId カテゴリID
     * @return カテゴリモデル
     */
    private CategoryModel getCategoryByIdOrNull(Long categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::selectById)
                .orElse(null);
    }
}