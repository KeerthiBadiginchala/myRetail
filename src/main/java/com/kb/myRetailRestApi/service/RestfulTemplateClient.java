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
	 * @Purpose: Consuming a Rest Service using HTTP GET method to retrieve all Product Names.
	 * getForObject() : Use HTTP GET method to retrieve data.
	 */
	public List<Product> getAllProductsNames(){
		Product[] productNameArray = template.getForObject(internalResourceUri, Product[].class);
		if(productNameArray==null)
			return null;
		List<Product> productNameList = Arrays.asList(productNameArray);
		return productNameList;
	}
	
	/*
	 * @Purpose: Consuming a Rest Service using HTTP GET method to retrieve Product Name for a given "Product Id".
	 * getForObject() : Use HTTP GET method to retrieve data.
	 * @Params: productId
	 */
	public Product getProductNameById(int productId){
		Product product = template.getForObject(internalResourceUri+productId, Product.class);
		return product;
	}
	
	/*
	 * @Purpose: Consuming a Rest Service using HTTP POST method to add Product Object.
	 * postForObject() : Creates a news resource using HTTP POST method.
	 * @Params: product
	 */
	public Product insertProduct(Product product) throws SQLException{
		Product productObj = template.postForObject(internalResourceUri, product, Product.class);
		return productObj;
	}
	
	/*
	 * @Purpose:Consuming a Rest Service using HTTP PUT method to edit the Product Object for a given "Product Id".
	 * put() : Updates the resource using HTTP PUT method.
	 * @Params: productId, product
	 */
	public void updateProduct(int productId, Product product) throws SQLException{
		template.put(internalResourceUri+productId, product);
	}
	
	/*
	 * @Purpose: Consuming a Rest Service using HTTP DELETE method to delete the Product Object for a given "Product Id".
	 * delete() : Deletes the resource using HTTP DELETE method.
	 * @Params: productId
	 */
	public void deleteProduct(int productId) throws SQLException{
		template.delete(internalResourceUri+productId);
	}

}
