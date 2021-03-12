package br.com.bookstore.book.book;

import br.com.bookstore.book.book.services.SaveBookServiceImpl;
import br.com.bookstore.book.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static br.com.bookstore.book.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade dos serviços responsáveis por salvar livro")
class SaveBookServiceTest {

    @Mock
    private BookRepository bookRepositoryMock;

    private SaveBookServiceImpl saveBookService;

    @BeforeEach
    void setUp() {
        this.saveBookService = new SaveBookServiceImpl(bookRepositoryMock);
    }

    @Test
    @DisplayName("salvar livro de devoluções quando bem-sucedido")
    void saveReturnsBookWhenSuccessful() {

        Book book = createBook().build();
        Set<Category> bookCategory = book.getCategories();
        saveBookService.insert(book);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepositoryMock, times(1)).save(bookArgumentCaptor.capture());

        Book result = bookArgumentCaptor.getValue();

        //verification
        assertAll("Book",
                ()-> assertThat(result.getTitle(), is("O Pequeno Príncipe")),
                ()-> assertThat(result.getSynopsis(), is("O Pequeno Príncipe representa a espontaneidade.")),
                ()-> assertThat(result.getIsbn(), is("978-3-16-148410-0")),
                ()-> assertThat(result.getAuthor(), is("Antoine de Saint")),
                ()-> assertThat(result.getYearOfPublication(), is(LocalDate.of(1943, 4, 6))),
                ()-> assertThat(result.getSellPrice(), is(10.00)),
                ()-> assertThat(result.getQuantityAvailable(), is(2)),
                ()-> assertThat(result.getCategories(), is(bookCategory))
        );
    }
}