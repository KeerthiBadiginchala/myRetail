package com.kb.myRetailRestApi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kb.myRetailRestApi.model.Price;


public interface MyRetailRepository extends MongoRepository<Price, Integer> {
	Price findByProductId(int prd_id);
}
