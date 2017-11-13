package com.kb.myRetailRestApi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kb.myRetailRestApi.model.Price;
import com.kb.myRetailRestApi.repository.MyRetailRepository;

@Service("myretailservice")
public class MyRetailService {

	@Autowired
	private MyRetailRepository myretailRepository;
	
	public List<Price> getAllProducts(){
		List<Price> priceList= myretailRepository.findAll();
		return priceList;
	}
	
	public Price getProductById(int prd_id){
		Price price = myretailRepository.findByProductId(prd_id);
		return price; 
	}
	
	public Price addPrice(Price price) throws SQLException{
		Price priceObj = myretailRepository.save(price);
		return priceObj;
		
	}
	
	public void updatePrice(Price price){
		myretailRepository.save(price);
		
	}
	
	public void deletePrice(int prd_id){
		
		myretailRepository.deleteByProductId(prd_id);
		
	}
	
}
