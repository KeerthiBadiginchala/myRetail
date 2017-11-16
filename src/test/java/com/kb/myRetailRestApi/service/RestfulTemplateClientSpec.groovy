package com.kb.myRetailRestApi.service

import java.util.Arrays
import java.util.List
import java.sql.SQLException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

import spock.lang.Shared
import spock.lang.Specification

import com.kb.myRetailRestApi.model.Price
import com.kb.myRetailRestApi.model.Product
import com.kb.myRetailRestApi.exception.ResourceNotFoundException

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

class RestfulTemplateClientSpec extends Specification{

	RestfulTemplateClient restfulTemplateClient
	@Shared
	def internalResourceUri = "http://localhost:8081/myretail/productNames/products/"


	def setup(){
		restfulTemplateClient = new RestfulTemplateClient(
				template: Mock(RestTemplate))
	}

	def 'Should return no products if not exist' (){

		when:
		ResponseEntity<List<Product>> responseEntity= restfulTemplateClient.getAllProductsNames()

		then:
		1 * restfulTemplateClient.template.getForObject(internalResourceUri,Product[].class)

	}

	def 'Should return all products if exist' (){
		setup:

		Product[] productNamesArray = [ new Product(
			"productId": 1,
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)")]

		Product[] expectedProductNamesResponseEntity = productNamesArray

		when:
		Product[] responseEntity = restfulTemplateClient.getAllProductsNames()

		then:
		1 * restfulTemplateClient.template.getForObject(internalResourceUri,Product[].class) >> productNamesArray

		assertThat(expectedProductNamesResponseEntity, equalTo(responseEntity))
	}

	def 'Should return no product if the given product id not exist' (){
		setup:
		int productId = 1

		when:
		ResponseEntity<Product> responseEntity= restfulTemplateClient.getProductNameById(productId)

		then:
		1 * restfulTemplateClient.template.getForObject(internalResourceUri+productId,Product.class)
	}
	def 'Should return the matched product if exist for the given product id' (){
		setup:
		int productId = 1

		Product productName = new Product(
				"productId": 1,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		Product expectedResponseEntity = productName

		when:
		Product responseEntity= restfulTemplateClient.getProductNameById(productId)

		then:
		1 * restfulTemplateClient.template.getForObject(internalResourceUri+productId,Product.class) >> productName

		assertThat(expectedResponseEntity, equalTo(responseEntity))
	}
	def 'Should add a product' (){

		setup:

		Product product = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		Product responseProductObj = new Product(
				"productId": 5,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		Product expectedResponseEntity = responseProductObj

		when:
		Product responseEntity = restfulTemplateClient.insertProduct(product)

		then:
		1 * restfulTemplateClient.template.postForObject(internalResourceUri, product, Product.class) >> responseProductObj

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}
	def 'Should update the product' (){

		setup:

		int productId = 5
		Product product = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		when:
		restfulTemplateClient.updateProduct(productId, product)

		then:
		1 * restfulTemplateClient.template.put(internalResourceUri+productId, product)

	}
	def 'Should delete the product' (){

		setup:

		int productId = 5

		when:
		restfulTemplateClient.deleteProduct(productId)

		then:
		1 * restfulTemplateClient.template.delete(internalResourceUri+productId)

	}
}