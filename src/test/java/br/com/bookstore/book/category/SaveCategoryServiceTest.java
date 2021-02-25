package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.SaveCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.bookstore.book.category.builders.CategoryBuilder.createCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for save category of book")
class SaveCategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepositoryMock;

    private SaveCategoryServiceImpl saveCategoryOfBookService;

    @BeforeEach
    void setUp() {
        this.saveCategoryOfBookService = new SaveCategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    @DisplayName("save created category of books when successful")
    void saveReturnsClientWhenSuccessful() {

        Category categoryOfBook = createCategory().build();

        saveCategoryOfBookService.insert(categoryOfBook);
        ArgumentCaptor<Category> captorCategoryOfBook = ArgumentCaptor.forClass(Category.class);

        verify(categoryRepositoryMock, times(1)).save(captorCategoryOfBook.capture());

        Category result = captorCategoryOfBook.getValue();

        assertAll("categoryofbook",
                () -> assertThat(result.getName(), is("Romance"))
        );
    }
}