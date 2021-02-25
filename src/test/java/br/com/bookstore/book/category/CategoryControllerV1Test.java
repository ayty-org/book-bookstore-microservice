package br.com.bookstore.book.category;

import br.com.bookstore.book.category.services.DeleteCategoryService;
import br.com.bookstore.book.category.services.GetCategoryService;
import br.com.bookstore.book.category.services.ListCategoryService;
import br.com.bookstore.book.category.services.ListPageCategoryService;
import br.com.bookstore.book.category.services.SaveCategoryService;
import br.com.bookstore.book.category.services.UpdateCategoryService;
import br.com.bookstore.book.category.v1.CategoryControllerV1;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
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

import static br.com.bookstore.book.category.builders.CategoryBuilder.createCategory;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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
@WebMvcTest(CategoryControllerV1.class)
@DisplayName("Validates the functionality of the controller responsible of category of book")
class CategoryControllerV1Test {
    private final String URL_CATEGORY = "/v1/api/book/category";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetCategoryService getCategoryService;

    @MockBean
    private ListCategoryService listCategoryService;

    @MockBean
    private ListPageCategoryService listPageCategoryService;

    @MockBean
    private SaveCategoryService saveCategoryService;

    @MockBean
    private DeleteCategoryService deleteCategoryService;

    @MockBean
    private UpdateCategoryService updateCategoryService;

    @Test
    @DisplayName("findById returns category of book when successful")
    void findByIdReturnCategoryOfBookWhenSuccessful() throws Exception {

        when(getCategoryService.findById(anyLong())).thenReturn(createCategory().build());

        mockMvc.perform(get(URL_CATEGORY + "/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Romance")));

        verify(getCategoryService).findById(anyLong());
    }

    @Test
    @DisplayName("findById throws CategoryOfBookNotFoundException when category of book is not found")
    void findByIdCategoryOfBookThrowCategoryOfBookNotFoundExceptionWhenCategoryOfBookNotFound() throws Exception {

        when(getCategoryService.findById(anyLong())).thenThrow( new CategoryNotFoundException());

        mockMvc.perform(get(URL_CATEGORY + "/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getCategoryService).findById(anyLong());
    }

    @Test
    @DisplayName("listAll returns list of category of book when successful")
    void listAllReturnsListOfCategoryOfBookWhenSuccessfull() throws Exception {

        when(listCategoryService.findAll()).thenReturn(Lists.newArrayList(
                createCategory().id(1L).build(),
                createCategory().id(2L).build(),
                createCategory().id(3L).build(),
                createCategory().id(4L).build()
        ));

        mockMvc.perform(get(URL_CATEGORY).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Romance")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Romance")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Romance")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("Romance"))
                );

        verify(listCategoryService).findAll();
    }

    @Test
    @DisplayName("listAll returns list of category of books inside page object when successful")
    void listAllReturnsListOfCategoryOfBookInsidePageObject_WhenSuccessful() throws Exception{

        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(createCategory().build()));

        Pageable pageable = PageRequest.of(0,2);

        when(listPageCategoryService.findPage(pageable)).thenReturn(categoryPage);


        mockMvc.perform(get(URL_CATEGORY + "/?page=0&size=2", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Romance")))
        ;

        verify(listPageCategoryService).findPage(pageable);
    }

    @Test
    @DisplayName("save returns category of book when successful")
    void saveReturnsCategoryOfBookWhenSuccessful() throws Exception{
        mockMvc.perform(post(URL_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("categoryDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(saveCategoryService).insert(any(Category.class));
    }

    @Test
    @DisplayName("update category of book when successful")
    void updateReturnsCategoryOfBookUpdateWhenSuccessful() throws Exception{
        mockMvc.perform(put(URL_CATEGORY + "/{id}" , 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("categoryUpdate.json")))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateCategoryService).update(any(Category.class), eq(1L));
    }

    @Test
    @DisplayName("delete remove category of book when successful")
    void deleteRemoveCategoryOfBookWhenSuccessful() throws Exception{
        mockMvc.perform(delete(URL_CATEGORY + "/{id}" , 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteCategoryService).delete(1L);
    }

    public static String readJson(String file) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/dataJson/" + file).toAbsolutePath());
        return new String(bytes);
    }
}