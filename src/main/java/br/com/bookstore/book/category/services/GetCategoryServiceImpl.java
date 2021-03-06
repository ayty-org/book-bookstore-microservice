package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.category.CategoryRepository;
import br.com.bookstore.book.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetCategoryServiceImpl implements GetCategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }
}
