package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import jp.blue_dolphin.ibooks.common.model.CategoryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * カテゴリリポジトリ
 */
@Repository
public interface CategoryRepository {
    /**
     * カテゴリIDとカテゴリ名のリストを取得する
     *
     * @return カテゴリIDとカテゴリ名のリスト
     */
    List<IdAndName> selectIdAndNames();

    /**
     * カテゴリを全件取得する
     *
     * @return カテゴリモデルリスト
     */
    List<CategoryModel> selectAll();

    /**
     * 引数のカテゴリモデルで更新する
     *
     * @param model     カテゴリモデル
     * @param createdId 更新者ID
     * @return カテゴリモデル
     */
    CategoryModel store(CategoryModel model, String createdId);

    /**
     * 引数のカテゴリを削除する
     *
     * @param model カテゴリモデル
     * @return 削除件数
     */
    int delete(CategoryModel model);
}
