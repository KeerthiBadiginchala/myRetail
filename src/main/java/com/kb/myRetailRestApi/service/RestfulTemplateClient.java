package com.kb.myRetailRestApi.service;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.kb.myRetailRestApi.model.Product;

/*
 * This class acts as a client to create RestTemplate instance, using which we can consume the REST services
 * from the internal resource hosted by myRetailRestApi application.
 * 
 */
public class RestfulTemplateClient {
	
	RestTemplate template;
	String internalResourceUri = "http://localhost:8081/myretail/productNames/products/";
	
	public RestfulTemplateClient(){
		template = new RestTemplate();
	}
	
	/*
	 * Consuming a Rest Service using HTTP GET method to retrieve all Product Names.
	 * getForObject() : Use HTTP GET method to retrieve data.
	 */
	public List<Product> getAllProductsNames(){
		Product[] prdNameArray = template.getForObject(internalResourceUri, Product[].class);
		if(prdNameArray==null)
			return null;
		List<Product> prdNameList = Arrays.asList(prdNameArray);
		return prdNameList;
	}
	
	/*
	 * Consuming a Rest Service using HTTP GET method to retrieve Product Name for a given "Product Id".
	 * getForObject() : Use HTTP GET method to retrieve data.
	 */
	public Product getProductNameById(int prd_Id){
		Product product = template.getForObject(internalResourceUri+prd_Id, Product.class);
		return product;
	}
	
	/*
	 * Consuming a Rest Service using HTTP POST method to add Product Object.
	 * postForObject() : Creates a news resource using HTTP POST method.
	 */
	public Product insertProduct(Product prd) throws SQLException{
		Product product = template.postForObject(internalResourceUri, prd, Product.class);
		return product;
	}
	
	/*
	 * Consuming a Rest Service using HTTP PUT method to edit the Product Object for a given "Product Id".
	 * put() : Updates the resource using HTTP PUT method.
	 */
	public void updateProduct(int product_id, Product prd) throws SQLException{
		template.put(internalResourceUri+product_id, prd);
	}
	
	/*
	 * Consuming a Rest Service using HTTP DELETE method to delete the Product Object for a given "Product Id".
	 * delete() : Deletes the resource using HTTP DELETE method.
	 */
	public void deleteProduct(int product_id) throws SQLException{
		template.delete(internalResourceUri+product_id);
	}

}
