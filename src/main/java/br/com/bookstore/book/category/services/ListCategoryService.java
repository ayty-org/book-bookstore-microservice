package br.com.bookstore.book.category.services;

import br.com.bookstore.book.category.Category;

import java.util.List;

@FunctionalInterface
public interface ListCategoryService {
    List<Category> findAll();
}