package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookRepository;
import br.com.bookstore.book.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetBookServiceImpl implements GetBookService{

    private final BookRepository bookRepository;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }
}
