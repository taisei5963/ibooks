package jp.blue_dolphin.ibooks.general.service;

import jp.blue_dolphin.ibooks.common.constant.SystemRegex;
import jp.blue_dolphin.ibooks.common.database.repository.BookGuideRepository;
import jp.blue_dolphin.ibooks.common.database.repository.BookRepository;
import jp.blue_dolphin.ibooks.common.model.BookGuideModel;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.util.Strings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ブックガイドサービス
 */
@AllArgsConstructor
@Service
public class BookGuideService {
    /** ブックガイドリポジトリ */
    private BookGuideRepository bookGuideRepository;
    /** ブックリポジトリ */
    private BookRepository bookRepository;

    /**
     * 引数のブックIDのブックガイド情報を取得する
     *
     * @param bookId ブックID
     * @return ブックガイドモデル
     */
    public Optional<BookGuideModel> selectByBookId(Long bookId) {
        return bookGuideRepository.selectByBookId(bookId);
    }

    /**
     * 引数のブックIDに紐づく次に読むべき書籍のタイトルと出版社のリストを返却する
     *
     * @param bookId ブックID
     * @return 次に読むべき書籍のタイトルと出版社のリスト
     */
    public List<String> getNextRecommendedTitleAndPublisher(Long bookId) {
        List<String> titleAndPublishers = new ArrayList<>();

        Optional<BookGuideModel> bookGuideOpt = selectByBookId(bookId);
        if (bookGuideOpt.isEmpty()) {
            return Collections.emptyList();
        }

        String nextRecommended = bookGuideOpt.get().getNextRecommended();
        String[] nextRecommends = nextRecommended.split("[\\n,・]+");

        for (String recommend : nextRecommends) {
            String target = recommend.trim();

            if (Strings.isEmpty(target)) {
                continue;
            }
            Pattern pattern = Pattern.compile(SystemRegex.NEXT_RECOMMENDED_REGEX);
            Matcher matcher = pattern.matcher(target);
            if (matcher.find()) {
                String title = matcher.group(1).trim();
                String publisher = matcher.group(2).trim();

                Optional<BookModel> bookOpt = bookRepository.selectByTitleAndPublisher(title, publisher);
                if (bookOpt.isEmpty()) {
                    return Collections.emptyList();
                } else {
                    titleAndPublishers.add(title);
                    titleAndPublishers.add(publisher);
                }
            }
        }
        return titleAndPublishers;
    }
}
