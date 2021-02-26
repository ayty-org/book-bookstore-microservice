package br.com.bookstore.book.book.v1;

import br.com.bookstore.book.book.Book;
import br.com.bookstore.book.book.BookDTO;
import br.com.bookstore.book.book.services.DeleteBookService;
import br.com.bookstore.book.book.services.GetBookService;
import br.com.bookstore.book.book.services.ListBookByCategoryService;
import br.com.bookstore.book.book.services.ListBookService;
import br.com.bookstore.book.book.services.ListPageBookService;
import br.com.bookstore.book.book.services.SaveBookService;
import br.com.bookstore.book.book.services.UpdateBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/book")
public class BookControllerV1 {

    private final GetBookService getBookService;
    private final ListBookService listBookService;
    private final ListBookByCategoryService listBookByCategoryService;
    private final ListPageBookService listPageBookService;
    private final SaveBookService saveBookService;
    private final DeleteBookService deleteBookService;
    private final UpdateBookService updateBookService;

    @GetMapping(value = "/{id}") //list book by id
    public BookDTO find(@PathVariable Long id) {
        return BookDTO.from(getBookService.findById(id));
    }

    @GetMapping//list all book
    public List<BookDTO> findAll(){
        return BookDTO.fromAll(listBookService.findAll());
    }

    @GetMapping(path = "/categoryname/{categoryName}") //list book by category name
    public List<BookDTO> findAllBooksByCategoryName(@PathVariable String categoryName){
        return BookDTO.fromAll(listBookByCategoryService.findAllBooksByCategoryName(categoryName));
    }

    @GetMapping(path = {"/"}) //list all book inside object page
    public Page<BookDTO> findPage(Pageable pageable) {
        return BookDTO.fromPage(listPageBookService.findPage(pageable));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping //create book
    public void insert(@Valid @RequestBody BookDTO bookDTO){
        saveBookService.insert(Book.to(bookDTO));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}") //replace book by id
    public void update(@Valid @RequestBody BookDTO bookDTO, @PathVariable Long id){
        updateBookService.update(bookDTO, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}") //delete book
    public void delete(@PathVariable Long id){
        deleteBookService.delete(id);
    }
}
