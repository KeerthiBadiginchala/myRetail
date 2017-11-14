package com.kb.myRetailRestApi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import com.kb.myRetailRestApi.model.Price;
//import com.kb.myRetailRestApi.exception.ResourceNotFoundException;
import com.kb.myRetailRestApi.model.Product;
import com.kb.myRetailRestApi.repository.ProductRepository;


/*
 * Service Layer to invoke DB related calls through ProductRepository which acts an as Mapper interface for myBatis
 * to connect to PostgresSQL DB to maintain ProductName details. 
 *
 */

@Service("productservice")
public class ProductService {
	
	@Autowired
	private ProductRepository productrepository;
	
	/*
	 * @Purpose: Method to fetch all ProductNames from PostgresSQL DB
	 */
	public List<Product> getAllProductNames(){
		List<Product> prdList= productrepository.getAllProductNames();
		return prdList;
	}
	
	/*
	 * @Purpose: Method to fetch ProductName for the given Product Id from PostgresSQL DB
	 * @Param: prd_id
	 */
	public Product getProductName(int prd_id){
		Product prd=productrepository.getProductByID(prd_id);
		return prd; 
	}
	

	/*
	 * @Purpose: Method to add Product details to PostgresSQL DB, for the given Product Object
	 * @Param: Product object 
	 * @Throws: SQLException
	 */
	public Product addProduct(Product product) throws SQLException{
		
		int status = productrepository.addProduct(product);
		Product prodObj = null;
		
		if(status == 1){
			prodObj = getProductName(product.getProductId());
		}
		return prodObj;
		
	}
	
	/*
	 * @Purpose: Method to edit Product details for the given Product Id
	 * @Param: Product object 
	 * @Throws: SQLException
	 */
	public void updateProduct(Product prd) throws SQLException{
		productrepository.updateProductName(prd);
	}
	
	/*
	 * @Purpose: Method to delete Product details for the given Product Id
	 * @Param: prd_id
	 * @Throws: SQLException
	 */
	public int deleteProduct(int prd_id) throws SQLException{
		int status = productrepository.deleteProductName(prd_id);
		return status;
	}

}
