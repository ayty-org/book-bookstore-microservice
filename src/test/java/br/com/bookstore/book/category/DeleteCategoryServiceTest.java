package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.DeleteCategoryService;
import br.com.bookstore.book.category.services.DeleteCategoryServiceImpl;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for delete category of book")
class DeleteCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    private DeleteCategoryServiceImpl deleteCategoryService;
    @BeforeEach
    void setUp() {
        this.deleteCategoryService = new DeleteCategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    @DisplayName("delete remove category of books when successful")
    void deleteRemoveCategoryOfBookWhenSuccessful() {
        when(categoryRepositoryMock.existsById(anyLong())).thenReturn(true);
        deleteCategoryService.delete(1L);
        verify(categoryRepositoryMock).existsById(anyLong());
    }

    @Test
    @DisplayName("delete throws CategoryOfBookNotFoundException when category of books is not found")
    void deleteThrowCategoryOfBookNotFoundExceptionWhenCategoryOfBookNotFound() {
        when(categoryRepositoryMock.existsById(anyLong())).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, ()-> deleteCategoryService.delete(1L));

        verify(categoryRepositoryMock, times(0)).deleteById(anyLong());
    }
}