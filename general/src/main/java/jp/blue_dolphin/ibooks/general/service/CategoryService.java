package jp.blue_dolphin.ibooks.general.service;

import jp.blue_dolphin.ibooks.common.database.repository.CategoryRepository;
import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * カテゴリIDとカテゴリ名のMapを取得する
     *
     * @return カテゴリIDをキー、カテゴリ名を値とするMap
     */
    public Map<Long, String> getCategoryNameMap(List<IdAndName> categories) {
        return categories.stream()
                .collect(Collectors.toMap(IdAndName::getId, IdAndName::getName));
    }
}
