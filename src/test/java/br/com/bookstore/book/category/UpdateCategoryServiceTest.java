package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.UpdateCategoryServiceImpl;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.bookstore.book.category.builders.CategoryBuilder.createCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for update category of book")
class UpdateCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    private UpdateCategoryServiceImpl updateCategoryService;

    @BeforeEach
    void setUp() {
        this.updateCategoryService = new UpdateCategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    @DisplayName("update category of book when successful")
    void updateReturnsCategoryOfBookUpdateWhenSuccessful(){
        Category categoryUpdated = createCategory().name("Terror").build();

        Category categoryOfBook = createCategory().build();
        Optional<Category> categoryOfBookOptional  = Optional.of(categoryOfBook);

        when(categoryRepositoryMock.findById(anyLong())).thenReturn(categoryOfBookOptional);

        updateCategoryService.update(categoryUpdated, 1L);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepositoryMock).save(categoryArgumentCaptor.capture());

        Category result = categoryArgumentCaptor.getValue();

        assertAll("CategoryOfBook",
                () -> assertThat(result.getName(), is(categoryUpdated.getName()))
        );
    }

    @Test
    @DisplayName("update throws CategoryOfBookNotFoundException when category of book is not found")
    void updateThrowCategoryOfBookNotFoundExceptionWhenCategoryOfBooktNotFound() {
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class,
                ()-> this.updateCategoryService.update(Category.builder().build(), 1L));

        verify(categoryRepositoryMock, times(0)).save(any());
    }
}