package com.kb.myRetailRestApi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kb.myRetailRestApi.model.Price;
import com.kb.myRetailRestApi.repository.MyRetailRepository;

@Service("myretailservice")
public class MyRetailService {

	@Autowired
	private MyRetailRepository myretailRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Price> getAllPriceDetails() throws SQLException{
		List<Price> priceList= myretailRepository.findAll();
		return priceList;
	}
	
	public Price getPriceByProductId(int prd_id) throws SQLException{
		Price price = myretailRepository.findByProductId(prd_id);
		return price; 
	}
	
	public Price addPrice(Price price) throws SQLException{
		throw new SQLException();
		//Price priceObj = myretailRepository.save(price);
		//return priceObj;
		
	}
	
	public void updatePrice(Price price) throws SQLException{
		
//		Price currentPriceObj = getPriceByProductId(price.getProductId());
//		currentPriceObj.setCurrencyCode(price.getCurrencyCode());
//		currentPriceObj.setPriceValue(price.getPriceValue());
//		myretailRepository.save(currentPriceObj);
		Query query = new Query();
		query.addCriteria(Criteria.where("productId").is(price.getProductId()));
		Update update = new Update();
		update.set("price_value", price.getPriceValue());
		update.set("currency_code", price.getCurrencyCode());
		mongoTemplate.updateFirst(query, update, Price.class);
		
	}
	
	public void deletePrice(int prd_id) throws SQLException{
		 
		myretailRepository.deleteByProductId(prd_id);
		
	}
	
}
