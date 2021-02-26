package br.com.bookstore.book.book.services;

import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListBookByCategoryServiceImpl implements ListBookByCategoryService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooksByCategoryName(String category) {
        return bookRepository.findBookByCategoriesName(category);
    }
}