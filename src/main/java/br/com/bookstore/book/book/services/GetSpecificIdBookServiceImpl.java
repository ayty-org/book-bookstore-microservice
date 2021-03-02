package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookRepository;
import br.com.bookstore.book.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSpecificIdBookServiceImpl implements GetSpecificIdBookService{

    private final BookRepository bookRepository;

    @Override
    public Book findBySpecificID(String specificID) {
        return bookRepository.findBySpecificID(specificID).orElseThrow(BookNotFoundException::new);
    }
}
