package com.kb.myRetailRestApi.service


import java.util.List
import java.sql.SQLException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

import spock.lang.Shared
import spock.lang.Specification

import com.kb.myRetailRestApi.model.Product
import com.kb.myRetailRestApi.exception.ResourceNotFoundException
import com.kb.myRetailRestApi.repository.ProductRepository

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

class ProductServiceSpec extends Specification{
	ProductService productService


	def setup(){
		productService = new ProductService(
				productrepository: Mock(ProductRepository))
	}

	def 'Should return no product name details if not exist in postgressql db' (){

		when:
		List<Product> responseEntity= productService.getAllProductNames()

		then:
		1 * productService.productrepository.getAllProductNames()

	}

	def 'Should return all product name deails if exist in postgressql db' (){
		setup:

		List<Product> productsNameList = [ new Product(
			"productId": 1,
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)")]

		List<Product> expectedProductNamesResponseEntity = productsNameList

		when:
		List<Product> responseEntity = productService.getAllProductNames()

		then:
		1 * productService.productrepository.getAllProductNames() >> productsNameList

		assertThat(expectedProductNamesResponseEntity, equalTo(responseEntity))
	}

	def 'Should return no product Names if the given product id not exist in postgressql db' (){
		setup:
		int productId = 1

		when:
		Product responseEntity= productService.getProductName(productId)

		then:
		1 * productService.productrepository.getProductByID(productId)
	}
	def 'Should return the matched product Name if exist in postgressql db for the given product id' (){
		setup:
		int productId = 1

		Product product = new Product(
				"productId": 1,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		Product expectedResponseEntity = product

		when:
		Product responseEntity= productService.getProductName(productId)

		then:
		1 * productService.productrepository.getProductByID(productId) >> product

		assertThat(expectedResponseEntity, equalTo(responseEntity))
	}

	def 'Should return null product if any exception in product insertion into postgressql db' (){

		setup:
		int status = 0
		Product product = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		Product expectedResponseEntity = null

		when:
		Product responseEntity = productService.addProduct(product)

		then:
		1 * productService.productrepository.addProduct(product) >> status
		0 * productService.getProductName(product.getProductId()) >> expectedResponseEntity

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}

	def 'Should add a product in postgressql db' (){

		setup:
		int status = 1
		Product product = new Product(
				"productId": 1,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		Product expectedResponseEntity = product

		when:
		Product responseEntity = productService.addProduct(product)

		then:
		1 * productService.productrepository.addProduct(product) >> status
		1 * productService.productrepository.getProductByID(1) >> product

		assertThat(expectedResponseEntity, equalTo(responseEntity))

	}
	def 'Should update the product name in postgressql db' (){

		setup:

		int productId = 3
		Product product = new Product(
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

		when:
		Product responseEntity = productService.updateProduct(product)

		then:
		1 * productService.productrepository.updateProductName(product)
	}
	def 'Should delete the product from postgressql db by product id' (){

		setup:

		int productId = 5

		when:
		Product responseEntity = productService.deleteProduct(productId)

		then:
		1 * productService.productrepository.deleteProductName(productId)

	}
}
