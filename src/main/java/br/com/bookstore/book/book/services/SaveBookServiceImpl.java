package br.com.bookstore.book.book.services;


import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookRepository;
import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.exceptions.BookAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SaveBookServiceImpl implements SaveBookService {

    private final BookRepository bookRepository;

    @Override
    public void insert(Book book) {
        if(bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistException();
        }

        if(!book.getCategories().isEmpty()){
            Set<Category> categorySet = new HashSet<>();
            for(Category category: book.getCategories()){
                category.getId();
                categorySet.add(category);
            }

            book.setCategories(categorySet);
        }

        bookRepository.save(book);
    }
}
