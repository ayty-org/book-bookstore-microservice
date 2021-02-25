package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.ListCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.bookstore.book.category.builders.CategoryBuilder.createCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for list all category of books")
class ListCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    private ListCategoryServiceImpl listCategoryOfBookService;

    @BeforeEach
    void setUp() {
        this.listCategoryOfBookService = new ListCategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    @DisplayName("listAll returns to list category of books when successful")
    void listAllReturnsListOfCategoryOfBooksWhenSuccessfull() {

        when(categoryRepositoryMock.findAll()).thenReturn(
                Stream.of(createCategory().name("Ação").build()).collect(Collectors.toList())
        );

        List<Category> result = this.listCategoryOfBookService.findAll();

        //verification
        assertAll("category",
                () -> assertThat(result.size(), is(1)),
                () -> assertThat(result.get(0).getName(), is("Ação"))
        );
    }
}