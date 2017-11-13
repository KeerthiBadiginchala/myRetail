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
		Product prd = productrepository.getProductByID(prd_id);
		return prd; 
	}
	
	public Product addProduct(Product product) throws SQLException{
		int status = productrepository.addProduct(product);
		Product prodObj = null;
		
		if(status == 1){
			prodObj = getProductName(product.getProductId());
		}
		return prodObj;
		
	}
	
	public void updateProduct(Product prd) throws SQLException{
		productrepository.updateProductName(prd);
	}
	
	
	public int deleteProduct(int prd_id) throws SQLException{
		int status = productrepository.deleteProduct(prd_id);
		return status;
	}

}
