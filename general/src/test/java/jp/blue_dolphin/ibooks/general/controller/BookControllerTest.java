package jp.blue_dolphin.ibooks.general.controller;

import jp.blue_dolphin.ibooks.common.dto.IdAndName;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.BookModel;
import jp.blue_dolphin.ibooks.common.service.MessageService;
import jp.blue_dolphin.ibooks.general.request.BookSearchForm;
import jp.blue_dolphin.ibooks.general.service.BookService;
import jp.blue_dolphin.ibooks.general.service.CategoryService;
import jp.blue_dolphin.ibooks.general.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	BookController controller;
	@Mock
	private BookService bookService;
	@Mock
	private CategoryService categoryService;
	@Mock
	private ReviewService reviewService;
	@Mock
	private MessageService messageService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	/**
	 * searchメソッドの正常系テスト<br>
	 * 検索結果が存在する場合に結果がモデルに設定され、一覧画面が表示されることを確認する
	 */
	@Test
	void testSearch_success() throws Exception {
		List<BookModel> books = List.of(getBookModelTestStub());
		SearchResult<BookModel> result = new SearchResult<>(books, 1L);
		List<IdAndName> categories = List.of(getIdAndNameTestStub());
		Map<Long, String> categoryMap = Map.of(1039124L, "IT");

		when(bookService.search(any(BookSearchForm.class), any(Pageable.class))).thenReturn(result);
		when(categoryService.selectIdAndNames()).thenReturn(categories);
		when(categoryService.getCategoryNameMap(categories)).thenReturn(categoryMap);

		mockMvc.perform(get("/book/search"))
				.andExpect(status().isOk())
				.andExpect(view().name("book/index"))
				.andExpect(model().attributeExists("page", "searchForm", "books", "categories", "categoryMap"))
				.andExpect(model().attribute("books", books));
	}

	@Test
	void testSearch_emptyResult() throws Exception {
		SearchResult<BookModel> result = new SearchResult<>(Collections.emptyList(), 0L);

		when(bookService.search(any(BookSearchForm.class), any(Pageable.class))).thenReturn(result);
		when(messageService.getMessage(eq("error.search.empty"), anyString())).thenReturn("該当するブックはありません");

		mockMvc.perform(get("/book/search"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("errors"))
				.andExpect(view().name("book/index"));
	}

	/**
	 * ブックモデルのテスト用スタブを返却する
	 * @return ブックモデルのテスト用スタブ
	 */
	private BookModel getBookModelTestStub() {
		return BookModel.builder()
				.bookId(1L)
				.janCode("9999999999999")
				.title("テスト")
				.subTitle("サブテスト")
				.author1("テスト作者１")
				.author2("テスト作者２")
				.translator("テスト翻訳者")
				.publisher("株式会社テスト")
				.totalRating(BigDecimal.valueOf(1))
				.categoryId1(12L)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.createdId("system")
				.ver(1)
				.build();
	}

	/**
	 * IdAndNameクラスのテスト用スタブを返却する
	 * @return IdAndNameクラスのテスト用スタブ
	 */
	private IdAndName getIdAndNameTestStub() {
		return IdAndName.builder()
				.id(12L)
				.name("その他・読み物")
				.build();
	}
}
