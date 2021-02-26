package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;

import java.util.List;

@FunctionalInterface
public interface ListBookByCategoryService {
    List<Book> findAllBooksByCategoryName(String name);
}