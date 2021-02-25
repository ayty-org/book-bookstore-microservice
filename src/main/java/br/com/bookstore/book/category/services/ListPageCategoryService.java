package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface ListPageCategoryService {
    Page<Category> findPage(Pageable pageable);
}
