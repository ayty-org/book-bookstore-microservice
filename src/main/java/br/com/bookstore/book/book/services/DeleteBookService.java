package br.com.bookstore.book.book.services;

@FunctionalInterface
public interface DeleteBookService {
    void delete(Long id);
}
