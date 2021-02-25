package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.ListPageCategoryServiceImpl;
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

import java.util.Collections;

import static br.com.bookstore.book.category.builders.CategoryBuilder.createCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for pagination of all categories")
class ListPageCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    private ListPageCategoryServiceImpl listPageCategoryService;

    @BeforeEach
    void setUp() {
        this.listPageCategoryService = new ListPageCategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    @DisplayName("listAll returns list to category of books inside page object when successful")
    void listAllReturnsListOfClientInsidePageObjectWhenSuccessful() {

        Pageable pageable = PageRequest.of(0,2);

        when(listPageCategoryService.findPage(pageable))
                .thenReturn(new PageImpl<>(Collections.nCopies(2, createCategory().build())));

        Page<Category> result = this.listPageCategoryService.findPage(pageable);

        //verification
        assertAll("category",
                ()-> assertThat(result.getNumber(),  is(0)),
                ()-> assertThat(result.getTotalElements(), is(2L)),
                ()-> assertThat(result.getTotalPages(), is(1)),
                ()-> assertThat(result.getSize(), is(2)),
                ()-> assertThat(result.getContent().get(0).getName(), is("Romance")),
                ()-> assertThat(result.getContent().get(1).getName(), is("Romance"))
        );
    }
}