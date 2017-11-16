package com.kb.myRetailRestApi.controller

import java.util.List
import java.sql.SQLException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import spock.lang.Specification

import com.kb.myRetailRestApi.model.Price
import com.kb.myRetailRestApi.model.Product
import com.kb.myRetailRestApi.exception.ResourceNotFoundException
import com.kb.myRetailRestApi.service.*

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

class MyRetailRestApiAppControllerSpec extends Specification {

	MyRetailRestApiAppController appController

	def setup(){
		appController = new MyRetailRestApiAppController(
				myretailService: Mock(MyRetailService),
				restTemplate: Mock(RestfulTemplateClient) )
	}

	def 'Should return no products if not exist' (){

		when:
		ResponseEntity<List<Product>> responseEntity= appController.getAllProducts()

		then:
		1 * appController.restTemplate.getAllProductsNames()
		0 * appController.myretailService.getPriceByProductId(1)

	}

	def 'Should return all products if exist' (){
		setup:
		List<Product> productsNameList = [ new Product(
			"productId": 1,
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)",
			"price": null)]

		ResponseEntity<List<Product>> expectedProductNamesResponseEntity = new ResponseEntity<>(productsNameList,HttpStatus.OK)

		when:
		ResponseEntity<List<Product>> responseEntity= appController.getAllProducts()

		then:
		1 * appController.restTemplate.getAllProductsNames() >> productsNameList
		1 * appController.myretailService.getPriceByProductId(1)

		assertThat(expectedProductNamesResponseEntity, equalTo(responseEntity))
	}

	def 'Should throw an exception if the given product id not exist' (){
		setup:
		int productId = 1

		when:
		ResponseEntity<Product> responseEntity= appController.getProductById(productId)

		then:
		thrown(ResourceNotFoundException)
	}

	def 'Should return the matched product if exist for the given product id' (){
		setup:
		int productId = 2
		Product productName = new Product(
				"productId": 2,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)",
				"price": null)

		ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(productName,HttpStatus.OK)

		when:
		ResponseEntity<Product> responseEntity= appController.getProductById(productId)

		then:
		1 * appController.restTemplate.getProductNameById(productId) >> productName
		1 * appController.myretailService.getPriceByProductId(productId)

		assertThat(expectedResponseEntity, equalTo(responseEntity))
	}

	def 'Should add a product' (){

		setup:

		Product productObj = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)",
				"price": new Price(
				"priceValue": 399.99,
				"currencyCode": "CAD"
				))
		Product responseProductObj = new Product(
				"productId": 5,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)",
				"price": new Price(
				"priceValue": 399.99,
				"currencyCode": "CAD"
				))

		ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(responseProductObj,HttpStatus.CREATED)

		when:
		ResponseEntity<Product> responseEntity= appController.addProduct(productObj)

		then:
		1 * appController.restTemplate.insertProduct(productObj) >> responseProductObj
		1 * appController.myretailService.addPrice(productObj.getPrice())

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}

	def 'Throw an exception when some error during product insertion' (){
		setup:

		Product productObj = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)",
				"price": new Price(
				"priceValue": 399.99,
				"currencyCode": "CAD"
				))
		when:
		ResponseEntity<Product> responseEntity= appController.addProduct(productObj)

		then:
		thrown(SQLException)
	}

	def 'Should update the product' (){

		setup:

		int productId = 5
		Product product = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)",
				"price": new Price(
				"priceValue": 399.99,
				"currencyCode": "CAD"
				))

		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>(HttpStatus.OK)

		when:
		ResponseEntity<String> responseEntity= appController.updateProduct(productId, product)

		then:
		1 * appController.restTemplate.updateProduct(productId, product)
		1 * appController.myretailService.updatePrice(product.getPrice())

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}

	def 'Should delete the product' (){

		setup:

		int productId = 5

		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT)

		when:
		ResponseEntity<String> responseEntity= appController.deleteProduct(productId)

		then:
		1 * appController.restTemplate.deleteProduct(productId)
		1 * appController.myretailService.deletePrice(productId)

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}
}