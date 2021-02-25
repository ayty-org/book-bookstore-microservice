package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;

@FunctionalInterface
public interface UpdateCategoryService {

    void update(Category category, Long id);
}
