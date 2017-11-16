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

/*
 * This class acts as a main controller to make Rest calls to retrieve/add/edit all Product Details:
	 * (1) Consumes the Rest Services to retrieve/add/edit Product Name(s), which is an internal resource hosted by the 
	 *     same application using RestfulTemplateClient
	 * (2) Retrieves the Price details stored in NOSQL DB
	 * (3) Integrates the both results and sends the consolidated Product object.     
	 * Example response: {
						    "id": 1,
						    "name": "The Big Lebowski (Blu-ray)(Widescreen)",
						    "current_price": {
						        "value": 199,
						        "currency_code": "USD"
						    }
						 }
 * 
 */
@RestController
@RequestMapping("/myretail")
public class MyRetailRestApiAppController {
	//private static final Logger log = LoggerFactory.getLogger(MyRetailRestApiAppController.class);

	@Autowired
	public MyRetailService myretailService;

	@Autowired
	private RestfulTemplateClient restTemplate;
	
	
	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() throws Exception{
		List<Product> productsNameList = restTemplate.getAllProductsNames();
		List<Product> productsList = new ArrayList<>();
		if(productsNameList != null){
			productsNameList.forEach(e -> {
				Price price = null;
				try {
					price = myretailService.getPriceByProductId(e.getProductId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.setPrice(price);
				productsList.add(e);
			});
		}
		return new ResponseEntity<List<Product>>(productsList,HttpStatus.OK);
	}

	@RequestMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) throws Exception{
		Product product = restTemplate.getProductNameById(productId);
		if(product == null) {
			throw new ResourceNotFoundException("No Product found for id: "+productId);
		}
		Price price = myretailService.getPriceByProductId(productId);
		product.setPrice(price);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/products", method= RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product productObj) throws Exception{
		Product product = restTemplate.insertProduct(productObj);
		
		if(product == null) {
			throw new SQLException("Not able to add the Product");
		}
		
		Price price = productObj.getPrice();
		price.setProductId(product.getProductId());		

		myretailService.addPrice(price);

		product.setPrice(price);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@PathVariable("id") int productId, @Valid @RequestBody Product product) throws Exception{
		product.setProductId(productId);
		restTemplate.updateProduct(productId, product);
		Price price = product.getPrice();
		price.setProductId(product.getProductId());	
		myretailService.updatePrice(price);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int productId) throws Exception{
		restTemplate.deleteProduct(productId);
		myretailService.deletePrice(productId);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}


}
