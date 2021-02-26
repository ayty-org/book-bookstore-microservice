package br.com.bookstore.book.book;

import br.com.bookstore.book.book.services.ListPageBookServiceImpl;
import br.com.bookstore.book.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static br.com.bookstore.book.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for pagination of all books")
class ListPageBookServiceTest {

    @Mock
    private BookRepository bookRepositoryMock;

    private ListPageBookServiceImpl listPageBookService;

    @BeforeEach
    void setUp() {
        this.listPageBookService = new ListPageBookServiceImpl(bookRepositoryMock);
    }

    @Test
    @DisplayName("listAll returns list of Book inside page object when successful")
    void listAllReturnsListOfBookInsidePageObjectWhenSuccessful() {

        Category categoryTest = new Category(1L,"Aventura");
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(categoryTest);

        Pageable pageable = PageRequest.of(0, 1);

        when(listPageBookService.findPage(pageable))
                .thenReturn(new PageImpl<>(Collections.nCopies(1, createBook().categories(categorySet).build())));

        Page<Book> result = listPageBookService.findPage(pageable);

        assertAll("Book",
                ()-> assertThat(result.getNumber(), is(0)),
                ()-> assertThat(result.getTotalElements(), is(1L)),
                ()-> assertThat(result.getTotalPages(), is(1)),
                ()-> assertThat(result.getSize(), is(1)),
                ()-> assertThat(result.getContent().get(0).getTitle(), is("O Pequeno Príncipe")),
                ()-> assertThat(result.getContent().get(0).getSinopse(), is("O Pequeno Príncipe representa a espontaneidade.")),
                ()-> assertThat(result.getContent().get(0).getIsbn(), is("978-3-16-148410-0")),
                ()-> assertThat(result.getContent().get(0).getAutor(), is("Antoine de Saint")),
                ()-> assertThat(result.getContent().get(0).getYearOfPublication(), is(LocalDate.of(1943, 4, 6))),
                ()-> assertThat(result.getContent().get(0).getSellPrice(), is(10.00)),
                ()-> assertThat(result.getContent().get(0).getQuantityAvailable(), is(2)),
                ()-> assertThat(result.getContent().get(0).getCategories(), is(categorySet))
        );
    }
}