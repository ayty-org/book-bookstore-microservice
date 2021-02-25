package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListPageCategoryServiceImpl implements ListPageCategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
