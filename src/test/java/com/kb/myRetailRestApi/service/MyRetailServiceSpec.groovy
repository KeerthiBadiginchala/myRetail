package com.kb.myRetailRestApi.service


import java.util.List
import java.sql.SQLException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

import spock.lang.Shared
import spock.lang.Specification

import com.kb.myRetailRestApi.model.Price
import com.kb.myRetailRestApi.exception.ResourceNotFoundException
import com.kb.myRetailRestApi.repository.MyRetailRepository

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

class MyRetailServiceSpec extends Specification{

	MyRetailService myretailService


	def setup(){
		myretailService = new MyRetailService(
				myretailRepository: Mock(MyRetailRepository),
				mongoTemplate: Mock(MongoTemplate))
	}

	def 'Should return no price details if not exist in mongo db' (){

		when:
		List<Price> responseEntity= myretailService.getAllPriceDetails()

		then:
		1 * myretailService.myretailRepository.findAll()

	}

	def 'Should return all price deails if exist in mongo db' (){
		setup:

		List<Price> priceList = [ new Price(
			"productId": 3,
			"priceValue": 399.99,
			"currencyCode": "CAD"
			)]

		List<Price> expectedProductNamesResponseEntity = priceList

		when:
		List<Price> responseEntity = myretailService.getAllPriceDetails()

		then:
		1 * myretailService.myretailRepository.findAll() >> priceList

		assertThat(expectedProductNamesResponseEntity, equalTo(responseEntity))
	}

	def 'Should return no price if the given product id not exist in mongo db' (){
		setup:
		int productId = 1

		when:
		Price responseEntity= myretailService.getPriceByProductId(productId)

		then:
		1 * myretailService.myretailRepository.findByProductId(productId)
	}
	def 'Should return the matched price if exist in mongo db for the given product id' (){
		setup:
		int productId = 1

		Price priceObj = new Price(
				"productId": 1,
				"priceValue": 399.99,
				"currencyCode": "CAD"
				)

		Price expectedResponseEntity = priceObj

		when:
		Price responseEntity= myretailService.getPriceByProductId(productId)

		then:
		1 * myretailService.myretailRepository.findByProductId(productId) >> priceObj

		assertThat(expectedResponseEntity, equalTo(responseEntity))
	}
	def 'Should add a price' (){

		setup:

		Price price = new Price(
				"productId": 3,
				"priceValue": 399.99,
				"currencyCode": "CAD"
				)

		Price expectedResponseEntity = price

		when:
		Price responseEntity = myretailService.addPrice(price)

		then:
		1 * myretailService.myretailRepository.save(price) >> price

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}
	def 'Should update the price' (){

		setup:

		int productId = 3
		Price price = new Price(
				"priceValue": 399.99,
				"currencyCode": "CAD"
				)
		Query query = new Query();
		query.addCriteria(Criteria.where("productId").is(price.getProductId()));
		Update update = new Update();
		update.set("price_value", price.getPriceValue());
		update.set("currency_code", price.getCurrencyCode());
		
		when:
		Price responseEntity = myretailService.updatePrice(price)

		then:
		1 * myretailService.mongoTemplate.updateFirst(query, update, Price.class)
	}
	def 'Should delete the product' (){

		setup:

		int productId = 5

		when:
		myretailService.deletePrice(productId)

		then:
		1 * myretailService.myretailRepository.deleteByProductId(productId)

	}

}