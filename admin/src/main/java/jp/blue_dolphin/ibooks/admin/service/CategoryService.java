package jp.blue_dolphin.ibooks.admin.service;

import jp.blue_dolphin.ibooks.common.database.repository.CategoryRepository;
import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カテゴリサービス
 */
@AllArgsConstructor
@Service
public class CategoryService {
    /** カテゴリリポジトリ */
    private CategoryRepository categoryRepository;

    /**
     * カテゴリIDとカテゴリ名のリストを取得する
     *
     * @return カテゴリIDとカテゴリ名のリスト
     */
    public List<IdAndName> selectIdAndNames() {
        return categoryRepository.selectIdAndNames();
    }
}
