package com.kb.myRetailRestApi.service;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.kb.myRetailRestApi.model.Product;

public class RestfulTemplateClient {
	
	RestTemplate template;
	String uri = "http://localhost:8081/myretail/productName/products/";
	
	public RestfulTemplateClient(){
		template = new RestTemplate();
	}
	
	public List<Product> getAllProductsNames(){
		Product[] prdNameArray = template.getForObject(uri, Product[].class);
		List<Product> prdNameList = Arrays.asList(prdNameArray);
		return prdNameList;
	}
	
	public Product getProductNameById(int prd_Id){
		String testuri = uri+prd_Id;
		System.out.println("testuri:"+testuri);
		Product product = template.getForObject(testuri, Product.class);
		System.out.println("after getProductNameById():"+product);
		return product;
	}
	
	public Product insertProduct(Product prd) throws SQLException{
		Product product = template.postForObject(uri, prd, Product.class);
		return product;
	}
	
	public void updateProduct(int product_id, Product prd) throws SQLException{
		template.put(uri+product_id, prd);
	}
	
	public void deleteProduct(int product_id) throws SQLException{
		template.delete(uri+product_id);
	}

}
