package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface ListPageBookService {
    Page<Book> findPage(Pageable pageable);
}
