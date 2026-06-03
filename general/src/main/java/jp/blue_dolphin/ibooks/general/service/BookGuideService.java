package jp.blue_dolphin.ibooks.general.service;

import jp.blue_dolphin.ibooks.common.database.repository.BookGuideRepository;
import jp.blue_dolphin.ibooks.common.model.BookGuideModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ブックガイドサービス
 */
@AllArgsConstructor
@Service
public class BookGuideService {
    /** ブックガイドリポジトリ */
    private BookGuideRepository bookGuideRepository;

    /**
     * 引数のブックIDのブックガイド情報を取得する
     *
     * @param bookId ブックID
     * @return ブックガイドモデル
     */
    public Optional<BookGuideModel> selectByBookId(Long bookId) {
        return bookGuideRepository.selectByBookId(bookId);
    }
}
