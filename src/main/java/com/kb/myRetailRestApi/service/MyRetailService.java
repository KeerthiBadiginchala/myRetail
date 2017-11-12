package com.kb.myRetailRestApi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kb.myRetailRestApi.model.Price;
import com.kb.myRetailRestApi.model.Product;
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
		System.out.println("getProductById, prd_id:"+prd_id);
		System.out.print("all:"+myretailRepository.findAll());
		Price price = myretailRepository.findByProductId(prd_id);
		System.out.println("price:"+price.toString());
//		Price price = new Price();
//		price.setCurrency_code("USD");
//		price.setPrice_value(new Float(199.99));
//		if(prd == null) {
//			throw new ResourceNotFoundException("Product doesn't exist");
//		}
		return price; 
	}
	
public Price addPrice(Price price) throws SQLException{
		
		System.out.println("MyRetail Service: addPrice:"+price.getProductId()+".."+ price.getPriceValue()+".."+ price.getCurrencyCode());
		Price priceObj = myretailRepository.save(price);
		return priceObj;
		
	}
}
