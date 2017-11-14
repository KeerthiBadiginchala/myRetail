package com.kb.myRetailRestApi.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;



//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
	public ResponseEntity<List<Product>> getAllProducts() throws Exception{
		List<Product> prdNameList = template.getAllProductsNames();
		List<Price> priceList = myretailService.getAllPriceDetails();
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
		
		if(prdNameList==null)
			throw new ResourceNotFoundException("No Products found");

		//Fetching Price object on each Product Object, it might need DB to hit for every Product Object
		prdNameList.forEach(e -> {
			Price price = null;
			try {
				price = myretailService.getPriceByProductId(e.getProductId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.setPrice(price);
			prdList.add(e);

		});
		return new ResponseEntity<List<Product>>(prdList,HttpStatus.OK);
	}

	@RequestMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int prd_Id) throws Exception{
		System.out.println("getProductById().."+prd_Id);
		Product product = template.getProductNameById(prd_Id);
		System.out.println("getProductById().."+product);
		if(product == null) {
			throw new ResourceNotFoundException("No Product found for id"+prd_Id);
		}
		Price price = myretailService.getPriceByProductId(prd_Id);
		product.setPrice(price);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/products", method= RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product prd) throws Exception{
		System.out.println("addProduct: "+prd);
		if(prd == null){
			System.out.println("prd is null block");
			return new ResponseEntity<Product>(new Product(),HttpStatus.BAD_REQUEST);
		}
		Product product = template.insertProduct(prd);
		
		
		if(product == null) {
			throw new SQLException("Not able to add the Product");
		}
		
		Price price = prd.getPrice();
		price.setProductId(product.getProductId());		

		myretailService.addPrice(price);

		product.setPrice(price);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@PathVariable("id") int product_id, @Valid @RequestBody Product prd) throws Exception{
		if(prd == null){
			System.out.println("updateProduct, prd is null block");
			return new ResponseEntity<String>("Request Body is empty",HttpStatus.BAD_REQUEST);
		}
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
