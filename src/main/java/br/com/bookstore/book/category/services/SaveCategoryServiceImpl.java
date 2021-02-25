package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveCategoryServiceImpl implements SaveCategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public void insert(Category category) {
        categoryRepository.save(category);
    }
}
