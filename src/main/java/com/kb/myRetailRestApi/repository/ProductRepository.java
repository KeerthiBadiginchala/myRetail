package com.kb.myRetailRestApi.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.kb.myRetailRestApi.model.Product;

/*
 * ProductRepository to represent PostgresSQL DB using Mybais Mappers to perform CRUD operation when needed.
 * This class is being used to maintain Product related details:
 *  {"productId": 1, "productName": "The Big Lebowski (Blu-ray)(Widescreen)"} 
 */

@Mapper
public interface ProductRepository {

	@Select("select product_id as productId, product_name as productName from public.Product where product_id=#{product_id}")
	Product getProductByID(int product_id);
	
	@Select("select product_id as productId, product_name as productName from Product")
	List<Product> getAllProductNames();
	
	@Insert("INSERT INTO public.PRODUCT (product_name) values(#{productName})")
	@SelectKey(keyProperty="productId",
    before=false, resultType=Integer.class, statement = {"SELECT max(product_id) from public.PRODUCT"})
	int addProduct(Product prd);
	
	@Update("Update public.PRODUCT set product_name=#{productName} where product_id=#{productId}")
	int updateProductName(Product product);
	
	@Delete("Delete from public.PRODUCT where product_id=#{product_id}")
	int deleteProductName(int product_id);
}
