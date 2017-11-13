package com.kb.myRetailRestApi.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kb.myRetailRestApi.model.Price;
import com.kb.myRetailRestApi.model.Product;
import com.kb.myRetailRestApi.service.MyRetailService;
import com.kb.myRetailRestApi.service.RestfulTemplateClient;
import com.kb.myRetailRestApi.exception.ResourceNotFoundException;


@RestController
@RequestMapping("/myretail")
public class MyRetailRestApiAppController {
	//private static final Logger log = LoggerFactory.getLogger(MyRetailRestApiAppController.class);

	@Autowired
	public MyRetailService myretailService;

	@Autowired
	private RestfulTemplateClient template;

	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> prdNameList = template.getAllProductsNames();
		List<Price> priceList = myretailService.getAllProducts();
		List<Product> prdList = new ArrayList<>();

		//		List<Product> result = prdNameList.stream()
		//			    .flatMap(one -> 
		//			    			priceList.stream().filter(two -> one.getProductId() == two.getProductId())
		//			        .map(two -> one.setPrice(two)))
		//			    .collect(Collectors.toList());

		//		prdNameList.stream().forEach(prdNameObj -> 
		//		{
		//			priceList.stream().filter(priceNameObj -> prdNameObj)
		//			
		//		});


		//Fetching Price object on each Product Object, it might need DB to hit for every Product Object
		prdNameList.forEach(e -> {
			Price price = myretailService.getProductById(e.getProductId());
			e.setPrice(price);
			prdList.add(e);

		});
		return new ResponseEntity<List<Product>>(prdList,HttpStatus.OK);
	}

	@RequestMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int prd_Id,HttpServletRequest request){
		Product product = template.getProductNameById(prd_Id);
		if(product == null) {
			throw new ResourceNotFoundException("No Product found for id"+prd_Id);
		}
		Price price = myretailService.getProductById(prd_Id);
		product.setPrice(price);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/products", method= RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product prd) throws Exception{
		Product product = template.insertProduct(prd);
		
		if(product == null) {
			throw new SQLException("Not able to add the Product for id"+prd.getProductId());
		}
		
		Price price = prd.getPrice();
		price.setProductId(product.getProductId());		

		myretailService.addPrice(price);

		product.setPrice(price);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@PathVariable("id") int product_id, @RequestBody Product prd) throws Exception{
		prd.setProductId(product_id);
		template.updateProduct(product_id, prd);
		Price price = prd.getPrice();
		price.setProductId(prd.getProductId());	
		myretailService.updatePrice(price);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int product_id) throws Exception{
		template.deleteProduct(product_id);
		myretailService.deletePrice(product_id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}


}
