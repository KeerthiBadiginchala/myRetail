# MyRetail: End-to-End POC for a Product's API
Exposes multiple rest api endpoints, which can be used to create a product, retrieve product(s), update and delete a product. This API aggregates product data from multiple sources (by the means of web services integration) and return it as JSON to the caller.  

Technologies Used:

| Technology    | Version       |
| ------------- | ------------- |
| Java          | 8  |
| SpringBoot  | 1.5.8  |
|Flyway DB|4.2.0 |
|Postgres|9.4.1212|
|MyBatis|3.4.5|
|Mongo|3.4.3|
|Spock Testing Framework|2.4|
|Groovy|2.4|
|Gradle|3.5.1  

## Steps to run the project on a local machine:
Note: Assuming Postgres and Mongo already installed  
1. Clone the project  
2. Update the `application.yaml` file under resources directory with respective Postgres and Mongo DB details.  
3. Run the `MyRetailRestApiApplication`, this will bring up the application and can be accessed via below URL:  

http://localhost:8081/myretail/products/

Internal Resource hosted by myRetail to fetch product name details:
http://localhost:8081/myretail/productNames/products/

End points: 
- GET: /products/  fetches all product details  
- GET: /products/{id} fetches single product details if found  
- POST: /products/ creates a new product  
- PUT: /products/{id} updates the product with new details  
- DELETE: /products/{id} deletes the product  

For more details please refer before Swagger API:


The following actions can be performed:  
* Responds to an HTTP GET request at "/products/" to retrive all product details and "/products/{id}" to retrieve specific product details and delivers the product data as JSON, where {id} will be a number.  
* Example response:

``` json
{  
  "productId": 1,  
  "productName": "The Big Lebowski (Blu-ray)(Widescreen)",  
  "price": {  
    "priceValue": 199,  
    "currencyCode": "USD"  
  }  
}
```
  
* Performs an HTTP GET to retrieve the product name from an internal resource (http://localhost:8081/myretail/productNames/products/) hosted by the same application
(http://localhost:8081/myretail/products/), which

    * Reads pricing information from a NoSQL data store,      
    * Reads the product id and product name from the HTTP request and  
    * Aggregates the both the data objects into one single response object in JSON 
    
* Similarly performs HTTP POST/PUT/DELETE opeations to create/update the product name from an internal resource and reads pricing information from a NoSQL data store and produces the single respose in JSON.   




