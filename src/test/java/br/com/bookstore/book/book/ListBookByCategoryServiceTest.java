package br.com.bookstore.book.book;

import br.com.bookstore.book.book.services.ListBookByCategoryServiceImpl;
import br.com.bookstore.book.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.bookstore.book.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for list all book by categories")
class ListBookByCategoryServiceTest {

    @Mock
    private BookRepository bookRepositoryMock;

    private ListBookByCategoryServiceImpl listBookByCategoryService;

    @BeforeEach
    void setUp() {
        this.listBookByCategoryService = new ListBookByCategoryServiceImpl(bookRepositoryMock);
    }

    @Test
    @DisplayName("listAll returns list of book by categories when successful")
    void listAllReturnsListOfBookWhenSuccessful() {
        Category categoryOfBookTest = new Category(1L,"Aventura");
        Set<Category> categoryOfBookSet = new HashSet<>();
        categoryOfBookSet.add(categoryOfBookTest);

        when(bookRepositoryMock.findBookByCategoriesName(anyString())).thenReturn(
                Stream.of(createBook().title("Title test").categories(categoryOfBookSet).build()).collect(Collectors.toList())
        );

        List<Book> result = this.listBookByCategoryService.findAllBooksByCategoryName("Aventura");

        assertAll("Book",
                () -> assertThat(result.size(), is(1)),
                () -> assertThat(result.get(0).getTitle(), is("Title test")),
                ()-> assertThat(result.get(0).getSinopse(), is("O Pequeno Príncipe representa a espontaneidade.")),
                ()-> assertThat(result.get(0).getIsbn(), is("978-3-16-148410-0")),
                ()-> assertThat(result.get(0).getAutor(), is("Antoine de Saint")),
                ()-> assertThat(result.get(0).getYearOfPublication(), is(LocalDate.of(1943, 4, 6))),
                ()-> assertThat(result.get(0).getSellPrice(), is(10.00)),
                ()-> assertThat(result.get(0).getQuantityAvailable(), is(2)),
                ()-> assertThat(result.get(0).getCategories(), is(categoryOfBookSet))

        );

        verify(bookRepositoryMock, times(1)).findBookByCategoriesName(anyString());
    }
}
