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
	
	public List<Product> getAllProductNames(){
		List<Product> prdList= productrepository.getAllProductNames();
		return prdList;
	}
	
	public Product getProductName(int prd_id){
		System.out.println("calling rep mthd,"+prd_id);
		Product prd=null;
		try{
			prd = productrepository.getProductByID(prd_id);
			//System.out.println(prd+"<<<<");
		}catch(Exception ex){
			System.out.println("calling rep mthd,exception block"+prd);
			ex.printStackTrace();
		}
		//System.out.println("after rep mthd,"+prd);
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
		int status = productrepository.deleteProductName(prd_id);
		return status;
	}

}
