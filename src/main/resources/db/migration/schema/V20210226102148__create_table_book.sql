CREATE TABLE tb_book (
    id int8 NOT NULL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    isbn VARCHAR(17) NOT NULL UNIQUE,
    sinopse VARCHAR(500) NOT NULL,
    autor VARCHAR(50) NOT NULL,
    year_of_publication DATE NOT NULL,
    sell_price float8 NOT NULL,
    quantity_available int4 NOT NULL,
    specificID uuid NOT NULL,
    category_id int8 NOT NULL
);

ALTER TABLE tb_book ADD CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES tb_category(id);