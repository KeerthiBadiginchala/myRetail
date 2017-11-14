package com.kb.myRetailRestApi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kb.myRetailRestApi.model.Price;

/*
 * MongoRepository to represent NOSQL/Mongo DB and perform CRUD operation when needed.
 * This class is being used to maintain Price related details:
 *  "price": {"priceValue": 199.99, "currencyCode": "USD"} 
 */
public interface MyRetailRepository extends MongoRepository<Price, Integer> {
	Price findByProductId(int prd_id);
	int deleteByProductId(int prd_id);
}
