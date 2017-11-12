SELECT * FROM PRODUCT_PRICE;

DROP TABLE PRODUCT_PRICE;
CREATE TABLE PRODUCT_PRICE (
    id bigserial PRIMARY key,
    product_id INT(8),
    price_value NUMERIC(10,3),
    currency_code VARCHAR2(3) 
);

INSERT INTO PRODUCT_PRICE (product_id, price_value, currency_code)
VALUES (1, 199.99, 'USD');

COMMIT;


db.PRODUCT_PRICE.insert({
   product_id: NumberInt(1), 
   price_value: 199.99,
   currency_code: 'USD'
})
db.PRODUCT_PRICE.find().pretty()
db.PRODUCT_PRICE.remove({"product_id":"1"})

db.PRODUCT_PRICE.drop()