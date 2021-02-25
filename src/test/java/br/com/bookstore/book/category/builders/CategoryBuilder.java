package br.com.bookstore.book.category.builders;

import br.com.bookstore.book.category.Category;

public class CategoryBuilder {

    public static Category.Builder createCategory (){
        return Category
                .builder()
                .id(1L)
                .name("Romance");
    };
}
