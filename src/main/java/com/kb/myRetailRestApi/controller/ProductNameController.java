package com.kb.myRetailRestApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

import com.kb.myRetailRestApi.model.Product;
import com.kb.myRetailRestApi.service.ProductService;


/*
 * This class is invoked by RestfulTemplateClient to Consumes the Rest Services to retrieve/add/edit Product Name(s)
	 * Example response: 
	    					{
							     "id": 1,
    							 "name": "The Big Lebowski (Blu-ray)(Widescreen)",
							     "current_price": null
							}
						 
 * 
 */
@RestController
@RequestMapping("/myretail/productNames")
public class ProductNameController {
	@Autowired
	public ProductService productservice;
	
	@RequestMapping("/products/{id}")
	public ResponseEntity<Product> getProductNameById(@PathVariable("id") int productId){
		Product product = productservice.getProductName(productId);
		
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> productList = productservice.getAllProductNames();
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

    @RequestMapping(value = "/products", method=RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws SQLException{
    	Product productObj=null;
		try {
			productObj = productservice.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<Product>(productObj, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/products/{id}", method= RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@PathVariable("id") int productId, @RequestBody Product product) throws SQLException{
		product.setProductId(productId);
		productservice.updateProduct(product);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int productId) throws SQLException{
		productservice.deleteProduct(productId);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

}
