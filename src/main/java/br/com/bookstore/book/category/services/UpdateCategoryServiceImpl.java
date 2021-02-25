package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.category.CategoryRepository;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateCategoryServiceImpl implements UpdateCategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public void update(Category category, Long id) {
        Category categorySaved = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        categorySaved.setName(category.getName());

        categoryRepository.save(categorySaved);
    }
}
