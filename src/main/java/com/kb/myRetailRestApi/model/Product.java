package com.kb.myRetailRestApi.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="PRODUCT")
public class Product {
	
	@Field("product_id")
	@JsonProperty("id")
	private int productId;
	
	@Field("product_name")
	@JsonProperty("name")
	@NotNull(message="Product Name can not be null")
	private String productName;
	
	@Valid
	@JsonProperty("current_price")
	private Price price; 
	
	public Product(){
		
	}
	
	
	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [product_id=" + productId + ", product_name="
				+ productName + ", price:"+price+"]";
	}
	

}
