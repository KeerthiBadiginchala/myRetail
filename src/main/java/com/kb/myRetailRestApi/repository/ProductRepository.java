package com.kb.myRetailRestApi.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.kb.myRetailRestApi.model.Product;

@Mapper
public interface ProductRepository {

	@Select("select product_id as productId, product_name as productName from Product where product_id = #{product_id}")
	Product getProductByID(int product_id);
	
	@Select("select product_id as productId, product_name as productName from Product")
	List<Product> getAllProducts();
	
	@Insert("INSERT INTO public.PRODUCT (product_name) values(#{productName})")
	@SelectKey(keyProperty="productId",
    before=false, resultType=Integer.class, statement = {"SELECT max(product_id) from public.PRODUCT"})
	int addProduct(Product prd);
}
