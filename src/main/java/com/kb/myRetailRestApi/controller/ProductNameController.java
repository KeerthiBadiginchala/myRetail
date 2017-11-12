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


@RestController
@RequestMapping("/myretail/productdetails")
public class ProductNameController {
	@Autowired
	public ProductService productservice;
	
	@RequestMapping("/products/{id}")
	public ResponseEntity<Product> getProductNameById(@PathVariable("id") int Prd_Id){
		Product prd = productservice.getProductName(Prd_Id);
		return new ResponseEntity<Product>(prd, HttpStatus.OK);
	}
	
	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> prdList = productservice.getAllProducts();
		return new ResponseEntity<List<Product>>(prdList, HttpStatus.OK);
	}

    @RequestMapping(value = "/products", method=RequestMethod.POST)
    public Product addProduct(@RequestBody Product prd){
    	System.out.println("Inside addproduct, prd"+prd.getProductName());
    	Product prdObj = null;
		try {
			prdObj = productservice.addProduct(prd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return prdObj;
    }

}
