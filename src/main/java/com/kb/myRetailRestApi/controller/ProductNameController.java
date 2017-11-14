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

import com.kb.myRetailRestApi.exception.ResourceNotFoundException;
import com.kb.myRetailRestApi.model.Product;
import com.kb.myRetailRestApi.service.ProductService;


@RestController
@RequestMapping("/myretail/productName")
public class ProductNameController {
	@Autowired
	public ProductService productservice;
	
	@RequestMapping("/products/{id}")
	public ResponseEntity<Product> getProductNameById(@PathVariable("id") int prd_Id){
		System.out.println("getProductNameById:"+prd_Id);
		Product prd = productservice.getProductName(prd_Id);
//		try{
//			System.out.println("after getProductNameById:"+prd);
//		}catch(Exception e){
//			System.out.println(e);
//		}
		
		return new ResponseEntity<Product>(prd, HttpStatus.OK);
	}
	
	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> prdList = productservice.getAllProductNames();
		return new ResponseEntity<List<Product>>(prdList, HttpStatus.OK);
	}

    @RequestMapping(value = "/products", method=RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product prd) throws SQLException{
    	Product prdObj=null;
		try {
			prdObj = productservice.addProduct(prd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<Product>(prdObj, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/products/{id}", method= RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@PathVariable("id") int product_id, @RequestBody Product prd) throws SQLException{
		prd.setProductId(product_id);
		productservice.updateProduct(prd);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int product_id) throws SQLException{
		productservice.deleteProduct(product_id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

}
