package br.com.bookstore.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyIsbnExistException extends RuntimeException {

    public BookAlreadyIsbnExistException(){
        super("Already isbn exist in Book");
    }
}
