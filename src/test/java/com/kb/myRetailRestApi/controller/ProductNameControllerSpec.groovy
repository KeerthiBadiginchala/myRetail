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

class ProductNameControllerSpec extends Specification {

		ProductNameController productNameController

		def setup(){
			productNameController = new ProductNameController(
			productservice: Mock(ProductService))
		}

		def 'Should return no products if not exist' (){

			when:
			ResponseEntity<List<Product>> responseEntity= productNameController.getAllProducts()

			then:
			1 * productNameController.productservice.getAllProductNames()

		}

		def 'Should return all products if exist' (){
			setup:
			List<Product> productsNameList = [ new Product(
				"productId": 1,
				"productName": "The Big Lebowski (Blu-ray)(Widescreen)")]

			ResponseEntity<List<Product>> expectedProductNamesResponseEntity = new ResponseEntity<>(productsNameList,HttpStatus.OK)

			when:
			ResponseEntity<List<Product>> responseEntity= productNameController.getAllProducts()

			then:
			1 * productNameController.productservice.getAllProductNames() >> productsNameList

			assertThat(expectedProductNamesResponseEntity, equalTo(responseEntity))
		}

		def 'Should return no product if the given product id not exist' (){
			setup:
			int productId = 1

			when:
			ResponseEntity<Product> responseEntity= productNameController.getProductNameById(productId)

			then:
			1 * productNameController.productservice.getProductName(productId)
		}

		def 'Should return the matched product if exist for the given product id' (){
			setup:
			int productId = 1
			
			Product productName = new Product(
			"productId": 1,
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

			ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(productName,HttpStatus.OK)

			when:
			ResponseEntity<Product> responseEntity= productNameController.getProductNameById(productId)

			then:
			1 * productNameController.productservice.getProductName(productId) >> productName

			assertThat(expectedResponseEntity, equalTo(responseEntity))
		}

		def 'Should add a product' (){

			setup:

			Product product = new Product(
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)")
			
			Product responseProductObj = new Product(
			"productId": 5,
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

			ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(responseProductObj,HttpStatus.CREATED)

			when:
			ResponseEntity<Product> responseEntity= productNameController.addProduct(product)

			then:
			1 * productNameController.productservice.addProduct(product) >> responseProductObj

			assertThat(expectedResponseEntity, equalTo(responseEntity))

		}

		def 'Should update the product' (){

			setup:

			int productId = 5
			Product product = new Product(
			"productName": "The Big Lebowski (Blu-ray)(Widescreen)")

			ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>(HttpStatus.OK)

			when:
			ResponseEntity<String> responseEntity= productNameController.updateProduct(productId, product)

			then:
			1 * productNameController.productservice.updateProduct(product)

			assertThat(expectedResponseEntity, equalTo(responseEntity))

		}

		def 'Should delete the product' (){

			setup:

			int productId = 5

			ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT)

			when:
			ResponseEntity<String> responseEntity= productNameController.deleteProduct(productId)

			then:
			1 * productNameController.productservice.deleteProduct(productId)

			assertThat(expectedResponseEntity, equalTo(responseEntity))

		}

	}
