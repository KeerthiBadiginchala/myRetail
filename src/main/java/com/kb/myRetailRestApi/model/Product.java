package com.kb.myRetailRestApi.model;

import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

public class Product {
	
	private int productId;
	
	@NotNull(message="Product Name can not be null")
	private String productName;
	private Price price; 
	
	Product(){
		
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
				+ productName + ", price:[ current_price:"+price.getPriceValue()+", currency_code:"+price.getCurrencyCode()+"]]";
	}
	

}
