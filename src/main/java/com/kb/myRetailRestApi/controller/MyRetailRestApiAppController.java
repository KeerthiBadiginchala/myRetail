package com.kb.myRetailRestApi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kb.myRetailRestApi.model.Price;
import com.kb.myRetailRestApi.model.Product;
import com.kb.myRetailRestApi.service.MyRetailService;


@RestController
@RequestMapping("/myretail")
public class MyRetailRestApiAppController {
	
	@Autowired
	public MyRetailService myretailService;
	
	@Autowired
    private RestTemplate template;
	
	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		Product[] prdNameArray = template.getForObject("http://localhost:8081/myretail/productdetails/products/", Product[].class);
		List<Product> prdNameList = Arrays.asList(prdNameArray);
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
		
		System.out.println("Inside getProductById(..)"+prd_Id+"..uriInfo:"+request.getServletContext().getContextPath()+"URI.."+request.getRequestURI()+"URL."+request.getRequestURL()+
				".."+request.getPathInfo()+".."+request.getRemoteAddr());
		//Way to get context path instead of hard coding
		Product product = template.getForObject("http://localhost:8081/myretail/productdetails/products/"+prd_Id, Product.class);
		System.out.println("prdName: "+product.getProductName());
		Price price = myretailService.getProductById(prd_Id);
		product.setPrice(price);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	/* ResponseEntity<Product> represents the whole HTTP response object to send the response the way you want to send
	 * i.e., Status, Headers and Response body.
	 */
	@RequestMapping(value = "/products", method= RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product prd) throws Exception{
		System.out.println("addProduct: "+prd.toString());
		Product product = template.postForObject("http://localhost:8081/myretail/productdetails/products/", prd, Product.class);
		
		Price price = prd.getPrice();
		price.setProductId(product.getProductId());		
				
				myretailService.addPrice(price);
		
		product.setPrice(price);
		//return Response.status((StatusType) Response.ok()).entity(prdoduct).build();
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}

//	@RequestMapping(value = "/products/{id}", method= RequestMethod.PUT)
//	public ResponseEntity<Product> updateProduct(@PathVariable("id") int product_id, @RequestBody Product prd) throws Exception{
//		prd.setProduct_id(product_id);
//		System.out.println("updateProduct: "+prd.toString());
//		Product product = productservice.updateProduct(prd);
//		return new ResponseEntity<Product>(product,HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/products/{id}", method= RequestMethod.DELETE)
//	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int product_id) throws Exception{
//		System.out.println("deleteProduct: "+product_id);
//		productservice.deleteProduct(product_id);
//		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
//	}
	
	
}
