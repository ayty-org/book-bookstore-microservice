package br.com.bookstore.book.book.builders;

import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.category.Category;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BookBuilder {
    public static Book.Builder createBook(){
        Category category = new Category(1L,"Aventura");
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(category);
        return Book.builder()
                .id(1L)
                .title("O Pequeno Príncipe")
                .sinopse("O Pequeno Príncipe representa a espontaneidade.")
                .isbn("978-3-16-148410-0")
                .autor("Antoine de Saint")
                .yearOfPublication(LocalDate.of(1943, 4, 6))
                .sellPrice(10.00)
                .quantityAvailable(2)
                .categories(categorySet);
    }
}
