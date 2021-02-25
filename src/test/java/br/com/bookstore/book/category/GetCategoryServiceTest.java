package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.GetCategoryServiceImpl;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.bookstore.book.category.builders.CategoryBuilder.createCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for searching for a category of book by id ")
class GetCategoryServiceTest {

    @Mock
    private CategoryRepository categoryOfBookRepositoryMock;

    private GetCategoryServiceImpl getCategoryOfBookService;

    @BeforeEach
    void setUp() {
        this.getCategoryOfBookService = new GetCategoryServiceImpl(categoryOfBookRepositoryMock);
    }

    @Test
    @DisplayName("findById returns category of book when succesful")
    void findByIdReturnCategoryOfBookWhenSuccessful(){
        Category categoryOfBook = createCategory().build(); //create a build to category of book

        Optional<Category> categoryOfBookSavedOptional = Optional.of(categoryOfBook);
        when(categoryOfBookRepositoryMock.findById(anyLong())).thenReturn(categoryOfBookSavedOptional);

        Category result = this.getCategoryOfBookService.findById(1L); //result of requisition

        //verification
        assertAll("categoryofbook",
                () -> assertThat(result.getName(), is("Romance"))
        );
    }

    @Test
    @DisplayName("findById throws CategoryOfBookNotFoundException when category of book is not found")
    void findByIdThrowCategoryOfBookNotFoundExceptionWhenCategoryOfBookNotFound() {
        when(categoryOfBookRepositoryMock.findById(anyLong())).thenThrow(new CategoryNotFoundException());

        assertThrows(CategoryNotFoundException.class, () -> getCategoryOfBookService.findById(1l));
    }
}