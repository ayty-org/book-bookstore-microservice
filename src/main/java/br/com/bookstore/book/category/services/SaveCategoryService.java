package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;

@FunctionalInterface
public interface SaveCategoryService {

    void insert(Category category);
}
