package com.kb.myRetailRestApi.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="PRODUCT_PRICE")
public class Price {
	
	@JsonIgnore
	@Field("product_id")
	@NumberFormat(style = Style.NUMBER)
	private int productId;
	
	@NumberFormat(style = Style.CURRENCY)
	@DecimalMax(value = "99999.999", message = "The priceValue can not be more than 99999.999")
	@DecimalMin(value = "1.00", message = "The priceValue can not be less than 1.00")
	@Field("price_value")
	private double priceValue;
	
	@Pattern(regexp = "^[^0-9]+$", message = "The currencyCode should be of characters only, no digits are allowed")
	@Length( max = 3, message = "The currencyCode should be of 3 characters")
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


	public double getPriceValue() {
		return priceValue;
	}


	public void setPriceValue(double priceValue) {
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
