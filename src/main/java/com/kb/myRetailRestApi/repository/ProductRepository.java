package com.kb.myRetailRestApi.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.kb.myRetailRestApi.model.Product;

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
	int updateProductName(Product prd);
	
	@Delete("Delete from public.PRODUCT where product_id=#{product_id}")
	int deleteProductName(int product_id);
}
