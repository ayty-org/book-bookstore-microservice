package br.com.bookstore.book.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdNot(String isbn, Long id);
    Optional<Book> findBySpecificID(String specificID);
    List<Book> findBookByCategoriesName(String categoryName);
}
