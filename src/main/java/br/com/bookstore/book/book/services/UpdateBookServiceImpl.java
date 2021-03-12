package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookDTO;
import br.com.bookstore.book.book.BookRepository;
import br.com.bookstore.book.exceptions.BookAlreadyIsbnExistException;
import br.com.bookstore.book.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateBookServiceImpl implements UpdateBookService {

    private final BookRepository bookRepository;

    @Override
    public void update(BookDTO bookDTO, Long id) {

        Book bookSaved = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        bookSaved.setAuthor(bookDTO.getAuthor());
        bookSaved.setCategories(bookDTO.getCategories());
        bookSaved.setIsbn(bookDTO.getIsbn());
        bookSaved.setQuantityAvailable(bookDTO.getQuantityAvailable());
        bookSaved.setSellPrice(bookDTO.getSellPrice());
        bookSaved.setSynopsis(bookDTO.getSynopsis());
        bookSaved.setTitle(bookDTO.getTitle());
        bookSaved.setYearOfPublication(bookDTO.getYearOfPublication());

       if(bookRepository.existsByIsbnAndIdNot(bookDTO.getIsbn(), id)){
           throw new BookAlreadyIsbnExistException();
       }
        bookRepository.save(bookSaved);
    }
}
