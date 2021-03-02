package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;

@FunctionalInterface
public interface GetSpecificIdBookService {
    Book findBySpecificID(String specificID);
}
