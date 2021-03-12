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

        Boolean existsByIsbnAndId = bookRepository.existsByIsbnAndId(bookSaved.getIsbn(), id);

        if(existsByIsbnAndId){
            throw new BookAlreadyIsbnExistException();
        }

        System.out.println(existsByIsbnAndId);

        bookSaved.setAuthor(bookDTO.getAuthor());
        bookSaved.setCategories(bookDTO.getCategories());
        bookSaved.setIsbn(bookDTO.getIsbn());
        bookSaved.setQuantityAvailable(bookDTO.getQuantityAvailable());
        bookSaved.setSellPrice(bookDTO.getSellPrice());
        bookSaved.setSynopsis(bookDTO.getSynopsis());
        bookSaved.setTitle(bookDTO.getTitle());
        bookSaved.setYearOfPublication(bookDTO.getYearOfPublication());

        bookRepository.save(bookSaved);
    }
}
