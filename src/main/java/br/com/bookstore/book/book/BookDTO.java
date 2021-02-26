package br.com.bookstore.book.book;

import br.com.bookstore.book.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class BookDTO {
    private Long id;

    private String title;

    private String sinopse;

    private String autor;

    private String isbn;

    private LocalDate yearOfPublication;

    private double sellPrice;

    private int quantityAvailable;

    private UUID specificID;

    private Set<Category> categories = new HashSet<>();

    public static BookDTO from(Book entity) {
        return BookDTO
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .sinopse(entity.getSinopse())
                .autor(entity.getAutor())
                .isbn(entity.getIsbn())
                .yearOfPublication(entity.getYearOfPublication())
                .sellPrice(entity.getSellPrice())
                .quantityAvailable(entity.getQuantityAvailable())
                .categories(entity.getCategories())
                .build();
    }

    public static List<BookDTO> fromAll(List<Book> books) {
        return books.stream().map(BookDTO::from).collect(Collectors.toList());
    }

    public static Page<BookDTO> fromPage(Page<Book> pages){
        return pages.map(BookDTO::from);
    }
}
