package com.techcof.ITive.general.service;

import com.techcof.ITive.common.database.repository.BookChapterRepository;
import com.techcof.ITive.common.model.BookChapterModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ブックチャプターサービス
 */
@AllArgsConstructor
@Service
public class BookChapterService {
    /** ブックチャプターリポジトリ */
    private BookChapterRepository bookChapterRepository;

    /**
     * 引数のブックIDに紐づくブックチャプターモデルリストを返却する
     *
     * @param bookId ブックID
     * @return ブックチャプターモデルリスト
     */
    public List<BookChapterModel> selectByBookId(long bookId) {
        return bookChapterRepository.selectByBookId(bookId);
    }
}
