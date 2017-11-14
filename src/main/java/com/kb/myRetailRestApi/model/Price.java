package com.kb.myRetailRestApi.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="PRODUCT_PRICE")
public class Price {
	@JsonIgnore
	@Field("product_id")
	private int productId;
	
	@Field("price_value")
	//@DecimalMax(value = "99999.999", message = "The decimal value can not be more than 99999.999")
	//@DecimalMin(value = "1.00", message = "The priceValue value can not be less than 1.00")
	//@Pattern(regexp = "{0-9}", message = "priceValue should be of decimal only")
	private BigDecimal priceValue;
	
	@Field("currency_code")
	//@Pattern(regexp = "^{0-9}", message = "currencyCode should be of string only")
	@Pattern(regexp="^(0|[1-9][0-9]*)$")
	//@Length(max = 3, message = "The currencyCode should be of 3 characters")
	private String currencyCode;
	
	
	public Price(){
		
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public BigDecimal getPriceValue() {
		return priceValue;
	}


	public void setPriceValue(BigDecimal priceValue) {
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
