package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;

@FunctionalInterface
public interface GetCategoryService {
    Category findById(Long id);
}
