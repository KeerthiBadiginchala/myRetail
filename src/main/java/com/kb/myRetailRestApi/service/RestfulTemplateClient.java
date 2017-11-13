package com.kb.myRetailRestApi.service;
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
		Product product = template.getForObject(uri+prd_Id, Product.class);
		return product;
	}
	
	public Product insertProduct(Product prd){
		Product product = template.postForObject(uri, prd, Product.class);
		return product;
	}
	
	public void updateProduct(int product_id, Product prd){
		template.put(uri+product_id, prd);
	}
	
	public void deleteProduct(int product_id){
		template.delete(uri+product_id);
	}

}
