package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.BookDTO;

@FunctionalInterface
public interface UpdateBookService {

    void update(BookDTO bookDTO, Long id);
}