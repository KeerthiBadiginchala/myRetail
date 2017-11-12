SELECT
    table_schema || '.' || table_name
FROM
    information_schema.tables
WHERE
    table_type = 'BASE TABLE'
AND
    table_schema NOT IN ('pg_catalog', 'information_schema');

SELECT * FROM INFORMATION_SCHEMA.sequences;

DROP TABLE product;
CREATE TABLE PRODUCT (
    product_id bigserial PRIMARY key,
    product_name varchar(50) NOT NULL
);


INSERT INTO PRODUCT (product_id, product_name)
VALUES (1, 'Stroller');

COMMIT;

SELECT * FROM product;