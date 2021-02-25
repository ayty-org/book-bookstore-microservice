package br.com.bookstore.book.category.v1;

import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.category.CategoryDTO;
import br.com.bookstore.book.category.services.DeleteCategoryService;
import br.com.bookstore.book.category.services.GetCategoryService;
import br.com.bookstore.book.category.services.ListCategoryService;
import br.com.bookstore.book.category.services.ListPageCategoryService;
import br.com.bookstore.book.category.services.SaveCategoryService;
import br.com.bookstore.book.category.services.UpdateCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/book/category")
public class CategoryControllerV1 {

    private final GetCategoryService getCategoryService;
    private final ListCategoryService listCategoryService;
    private final ListPageCategoryService listPageCategoryService;
    private final DeleteCategoryService deleteCategoryService;
    private final SaveCategoryService saveCategoryService;
    private final UpdateCategoryService updateCategoryService;

    @GetMapping(value = "/{id}")
    public CategoryDTO find(@PathVariable Long id){
        return CategoryDTO.from(getCategoryService.findById(id));
    }

    @GetMapping
    public List<CategoryDTO> findAll(){
        return CategoryDTO.fromAll(listCategoryService.findAll());
    }

    @GetMapping(path = {"/"})
    public Page<CategoryDTO> findPage(Pageable pageable) {
        return CategoryDTO.fromPage(listPageCategoryService.findPage(pageable));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void insert(@Valid @RequestBody CategoryDTO categoryDTO) {
        saveCategoryService.insert(Category.to(categoryDTO));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}")
    public void update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        updateCategoryService.update(Category.to(categoryDTO), id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public  void delete(@PathVariable Long id) {
        deleteCategoryService.delete(id);
    }
}
