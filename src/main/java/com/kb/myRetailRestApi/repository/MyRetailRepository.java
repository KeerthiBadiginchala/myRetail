package com.kb.myRetailRestApi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kb.myRetailRestApi.model.Price;

/*
 * MongoRepository to represent NOSQL/Mongo DB and perform CRUD operation when needed.
 * This class is being used to maintain Price related details:
 *  "current_price": {
        "value": 199,
        "currency_code": "USD"
    }
 */
public interface MyRetailRepository extends MongoRepository<Price, Integer> {
	Price findByProductId(int productId);
	int deleteByProductId(int productId);
}
