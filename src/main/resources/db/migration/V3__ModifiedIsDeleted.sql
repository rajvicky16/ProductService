ALTER TABLE categories
    MODIFY is_deleted BIT (1) NULL;

ALTER TABLE products
    MODIFY is_deleted BIT (1) NULL;