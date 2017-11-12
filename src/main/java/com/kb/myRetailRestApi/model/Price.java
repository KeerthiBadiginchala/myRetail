package com.kb.myRetailRestApi.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="PRODUCT_PRICE")
public class Price {
	@JsonIgnore
	@Field("product_id")
	private int productId;
	@Field("price_value")
	private float priceValue;
	@Field("currency_code")
	private String currencyCode;
	
	
	public Price(){
		
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public float getPriceValue() {
		return priceValue;
	}


	public void setPriceValue(float priceValue) {
		this.priceValue = priceValue;
	}


	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	@Override
	public String toString() {
		return "Price [productId=" + productId + ", priceValue=" + priceValue
				+ ", currencyCode=" + currencyCode + "]";
	}


	
	
}
