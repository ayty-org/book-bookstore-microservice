package br.com.bookstore.book.book.services;


import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookRepository;
import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.exceptions.BookAlreadyIsbnExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SaveBookServiceImpl implements SaveBookService {

    private final BookRepository bookRepository;

    @Override
    public void insert(Book book) {
        if(bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyIsbnExistException();
        }

        if(!book.getCategories().isEmpty()){
            Set<Category> categorySet = new HashSet<>();
            for(Category category: book.getCategories()){
                categorySet.add(category);
            }

            book.setCategories(categorySet);
        }
        book.setSpecificID(UUID.randomUUID().toString());
        bookRepository.save(book);
    }
}
