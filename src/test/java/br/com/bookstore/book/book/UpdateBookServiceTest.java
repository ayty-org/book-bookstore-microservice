package br.com.bookstore.book.book;

import br.com.bookstore.book.book.services.UpdateBookServiceImpl;
import br.com.bookstore.book.category.Category;
import br.com.bookstore.book.exceptions.BookAlreadyIsbnExistException;
import br.com.bookstore.book.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static br.com.bookstore.book.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for update book")
class UpdateBookServiceTest {

    @Mock
    private BookRepository bookRepositoryMock;

    private UpdateBookServiceImpl updateBookService;

    @BeforeEach
    void setUp() {
        this.updateBookService = new UpdateBookServiceImpl(bookRepositoryMock);
    }

    @Test
    @DisplayName("atualizar livro quando bem sucedido")
    void updateReturnsBookUpdateWhenSuccessful(){

        Book putBookRequest = createBook()
                .title("New Title")
                .quantityAvailable(10)
                .build();
        Set<Category> bookCategory = putBookRequest.getCategories();

        Optional<Book> bookOptional = Optional.of(createBook().build());
        when(bookRepositoryMock.findById(anyLong())).thenReturn(bookOptional);

        updateBookService.update(BookDTO.from(putBookRequest), 1L);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepositoryMock).save(bookArgumentCaptor.capture());

        Book result = bookArgumentCaptor.getValue();

        //verification
        assertAll("Book",
                ()-> assertThat(result.getTitle(), is("New Title")),
                ()-> assertThat(result.getSynopsis(), is("O Pequeno Príncipe representa a espontaneidade.")),
                ()-> assertThat(result.getIsbn(), is("978-3-16-148410-0")),
                ()-> assertThat(result.getAuthor(), is("Antoine de Saint")),
                ()-> assertThat(result.getYearOfPublication(), is(LocalDate.of(1943, 4, 6))),
                ()-> assertThat(result.getSellPrice(), is(10.00)),
                ()-> assertThat(result.getQuantityAvailable(), is(10)),
                ()-> assertThat(result.getCategories(), is(bookCategory))
        );
    }

    @Test
    @DisplayName("update lança BookNotFoundException quando o livro não é encontrado")
    void updateThrowBookNotFoundExceptionWhenBookNotFound() {
        when(bookRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, ()-> this.updateBookService.update(BookDTO.builder().build(), 1L));

        verify(bookRepositoryMock, times(0)).save(any());
    }

    @Test
    @DisplayName("update lança BookNotFoundException quando o isbn do livro atualizado já existia no livro salvo")
    void updateThrowBookAlreadyIsbnExistExceptionWhenBookIsbnAlreadyExists() {
        Book book = createBook().build();
        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepositoryMock.findById(anyLong())).thenReturn(bookOptional);
        when(bookRepositoryMock.existsByIsbnAndIdNot(bookOptional.get().getIsbn(), bookOptional.get().getId())).thenReturn(
                Boolean.TRUE
        );

        assertThrows(BookAlreadyIsbnExistException.class, ()-> this.updateBookService.update(BookDTO.from(book), 1L));

        verify(bookRepositoryMock, times(0)).save(any());
    }
}
