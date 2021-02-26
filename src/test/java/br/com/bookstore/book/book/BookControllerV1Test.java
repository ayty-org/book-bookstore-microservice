package br.com.bookstore.book.book;

import br.com.bookstore.book.book.services.DeleteBookService;
import br.com.bookstore.book.book.services.GetBookService;
import br.com.bookstore.book.book.services.ListBookByCategoryService;
import br.com.bookstore.book.book.services.ListBookService;
import br.com.bookstore.book.book.services.ListPageBookService;
import br.com.bookstore.book.book.services.SaveBookService;
import br.com.bookstore.book.book.services.UpdateBookService;
import br.com.bookstore.book.book.v1.BookControllerV1;
import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.exceptions.BookNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static br.com.bookstore.book.book.builders.BookBuilder.createBook;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookControllerV1.class)
@DisplayName("Validates the functionality of the controller responsible of book")
class BookControllerV1Test {

    private final String URL_BOOK = "/v1/api/book";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeleteBookService deleteBookService;

    @MockBean
    private GetBookService getBookService;

    @MockBean
    private ListBookByCategoryService listBookByCategoryService;

    @MockBean
    private ListBookService listBookService;

    @MockBean
    private ListPageBookService listPageBookService;

    @MockBean
    private SaveBookService saveBookService;

    @MockBean
    private UpdateBookService updateBookService;

    @Test
    @DisplayName("findById return book when succesful")
    void findByIdReturnBookWhenSuccessful() throws Exception{

        when(getBookService.findById(anyLong())).thenReturn(createBook().build());

        mockMvc.perform(get(URL_BOOK + "/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("O Pequeno Príncipe")))
                .andExpect(jsonPath("$.sinopse", is("O Pequeno Príncipe representa a espontaneidade.")))
                .andExpect(jsonPath("$.isbn", is("978-3-16-148410-0")))
                .andExpect(jsonPath("$.autor", is("Antoine de Saint")))
                .andExpect(jsonPath("$.yearOfPublication", is("1943-04-06")))
                .andExpect(jsonPath("$.sellPrice", is(10.00)))
                .andExpect(jsonPath("$.quantityAvailable", is(2)))
                .andExpect(jsonPath("$.categories.[0].id", is(1)))
                .andExpect(jsonPath("$.categories.[0].name", is("Aventura")));

        verify(getBookService).findById(anyLong());
    }

    @Test
    @DisplayName("findById throws BookNotFoundException when book is not found")
    void findByIdBookThrowBookNotFoundExceptionWhenBookNotFound() throws Exception {

        when(getBookService.findById(anyLong())).thenThrow(new BookNotFoundException());

        mockMvc.perform(get(URL_BOOK + "/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getBookService).findById(1L);
    }

    @Test
    @DisplayName("listAll returns list of book when successful")
    void listAllReturnsListOfBookWhenSuccessfull() throws Exception {

        when(listBookService.findAll()).thenReturn(Lists.newArrayList(
                createBook().id(1L).build(),
                createBook().id(2L).build()
        ));

        mockMvc.perform(get(URL_BOOK).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("O Pequeno Príncipe")))
                .andExpect(jsonPath("$[0].sinopse", is("O Pequeno Príncipe representa a espontaneidade.")))
                .andExpect(jsonPath("$[0].isbn", is("978-3-16-148410-0")))
                .andExpect(jsonPath("$[0].autor", is("Antoine de Saint")))
                .andExpect(jsonPath("$[0].yearOfPublication", is("1943-04-06")))
                .andExpect(jsonPath("$[0].sellPrice", is(10.00)))
                .andExpect(jsonPath("$[0].quantityAvailable", is(2)))
                .andExpect(jsonPath("$[0].categories.[0].id", is(1)))
                .andExpect(jsonPath("$[0].categories.[0].name", is("Aventura")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("O Pequeno Príncipe")))
                .andExpect(jsonPath("$[1].sinopse", is("O Pequeno Príncipe representa a espontaneidade.")))
                .andExpect(jsonPath("$[1].isbn", is("978-3-16-148410-0")))
                .andExpect(jsonPath("$[1].autor", is("Antoine de Saint")))
                .andExpect(jsonPath("$[1].yearOfPublication", is("1943-04-06")))
                .andExpect(jsonPath("$[1].sellPrice", is(10.00)))
                .andExpect(jsonPath("$[1].quantityAvailable", is(2)))
                .andExpect(jsonPath("$[1].categories.[0].id", is(1)))
                .andExpect(jsonPath("$[1].categories.[0].name", is("Aventura")));

        verify(listBookService).findAll();
    }

    @Test
    @DisplayName("listAll returns list of book inside page object when successful")
    void listAllReturnsListOfBookInsidePageObject_WhenSuccessful() throws Exception{

        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(createBook().id(1L).build()));

        Pageable pageable = PageRequest.of(0,2);

        when(listPageBookService.findPage(pageable)).thenReturn(bookPage);

        mockMvc.perform(get(URL_BOOK + "/?page=0&size=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].title", is("O Pequeno Príncipe")))
                .andExpect(jsonPath("$.content[0].sinopse", is("O Pequeno Príncipe representa a espontaneidade.")))
                .andExpect(jsonPath("$.content[0].isbn", is("978-3-16-148410-0")))
                .andExpect(jsonPath("$.content[0].autor", is("Antoine de Saint")))
                .andExpect(jsonPath("$.content[0].yearOfPublication", is("1943-04-06")))
                .andExpect(jsonPath("$.content[0].sellPrice", is(10.00)))
                .andExpect(jsonPath("$.content[0].quantityAvailable", is(2)))
                .andExpect(jsonPath("$.content[0].categories.[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].categories.[0].name", is("Aventura")));

        verify(listPageBookService).findPage(pageable);
    }

    @Test
    @DisplayName("listAll returns list of book by categories when successful")
    void listAllByCategoryReturnsListOfBookWhenSuccessful() throws Exception {
        Category categoryOfBook = new Category(1L,"Aventura");

        String categoryName = categoryOfBook.getName();

        when(listBookByCategoryService.findAllBooksByCategoryName(categoryName)).thenReturn(Collections.singletonList(createBook().id(1L).build()));

        mockMvc.perform(get(URL_BOOK + "/categoryname/{categoryName}", categoryName).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("O Pequeno Príncipe")))
                .andExpect(jsonPath("$[0].sinopse", is("O Pequeno Príncipe representa a espontaneidade.")))
                .andExpect(jsonPath("$[0].isbn", is("978-3-16-148410-0")))
                .andExpect(jsonPath("$[0].autor", is("Antoine de Saint")))
                .andExpect(jsonPath("$[0].yearOfPublication", is("1943-04-06")))
                .andExpect(jsonPath("$[0].sellPrice", is(10.00)))
                .andExpect(jsonPath("$[0].quantityAvailable", is(2)))
                .andExpect(jsonPath("$[0].categories.[0].id", is(1)))
                .andExpect(jsonPath("$[0].categories.[0].name", is("Aventura")));

        verify(listBookByCategoryService).findAllBooksByCategoryName(categoryName);
    }

    @Test
    @DisplayName("save returns book when successful")
    void saveReturnsBookWhenSuccessful() throws Exception{

        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("bookDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(saveBookService).insert(any(Book.class));
    }

    @Test
    @DisplayName("save throws book when title is null")
    void saveThrowBadRequestWhenTitleIsNull() throws Exception{

        Book book = createBook().id(1L).title(null).build();
        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveBookService, times(0)).insert(any(Book.class));
    }

    @Test
    @DisplayName("save throws book when sinopse is null")
    void saveThrowBadRequestWhenSinopseIsNull() throws Exception{

        Book book = createBook().id(1L).sinopse(null).build();
        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveBookService, times(0)).insert(any(Book.class));
    }

    @Test
    @DisplayName("save throws book when autor is null")
    void saveThrowBadRequestWhenAutorIsNull() throws Exception{

        Book book = createBook().id(1L).autor(null).build();
        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveBookService, times(0)).insert(any(Book.class));
    }

    @Test
    @DisplayName("save throws book when isbn is null")
    void saveThrowBadRequestWhenIsbnIsNull() throws Exception{

        Book book = createBook().id(1L).isbn(null).build();
        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveBookService, times(0)).insert(any(Book.class));
    }

    @Test
    @DisplayName("save throws book when sel price is negative")
    void saveThrowBadRequestWhenSellPriceIsNegative() throws Exception{

        Book book = createBook().id(1L).sellPrice(-1).build();
        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveBookService, times(0)).insert(any(Book.class));
    }

    @Test
    @DisplayName("save throws book when yearOfPublication is null")
    void saveThrowBadRequestWhenYearOfPublicationIsNull() throws Exception{

        Book book = createBook().id(1L).yearOfPublication(null).build();
        mockMvc.perform(post(URL_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveBookService, times(0)).insert(any(Book.class));
    }

    @Test
    @DisplayName("update book when successful")
    void updateReturnsBookUpdateWhenSuccessful() throws Exception{
        mockMvc.perform(put(URL_BOOK + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("bookUpdate.json")))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateBookService).update(any(BookDTO.class), eq(1L));
    }

    @Test
    @DisplayName("delete remove books when successful")
    void deleteRemoveBookWhenSuccessful() throws Exception{
        mockMvc.perform(delete(URL_BOOK + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteBookService).delete(anyLong());
    }

    public static String readJson(String file) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/dataJson/" + file).toAbsolutePath());
        return new String(bytes);
    }
}