package br.com.bookstore.book.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

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

    private UUID purchaseID;
}
