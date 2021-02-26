package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.BookRepository;
import br.com.bookstore.book.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteBookServiceImpl implements DeleteBookService{

    private final BookRepository bookRepository;

    @Override
    public void delete(Long id) {
        if(!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
    }
}
