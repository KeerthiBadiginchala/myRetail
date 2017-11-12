package com.kb.myRetailRestApi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.kb.myRetailRestApi.model.Price;
//import com.kb.myRetailRestApi.exception.ResourceNotFoundException;
import com.kb.myRetailRestApi.model.Product;
import com.kb.myRetailRestApi.repository.ProductRepository;

@Service("productservice")
public class ProductService {
	
	@Autowired
	private ProductRepository productrepository;
	
	public List<Product> getAllProducts(){
		List<Product> prdList= productrepository.getAllProducts();
		return prdList;
	}
	
	public Product getProductName(int prd_id){
		System.out.println("getProductByID, prd_id:"+prd_id+","+productrepository);
		Product prd = productrepository.getProductByID(prd_id);
		return prd; 
	}
	
	public Product addProduct(Product product) throws SQLException{
		
		System.out.println("Product Service: addProduct:"+product.getProductName());
		int status = productrepository.addProduct(product);
		System.out.println("status:"+status);
		Product prodObj = null;
		
		if(status == 1){
			prodObj = getProductName(product.getProductId());
		}
		return prodObj;
		
	}

}
