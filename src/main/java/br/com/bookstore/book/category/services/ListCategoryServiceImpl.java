package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListCategoryServiceImpl implements ListCategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
