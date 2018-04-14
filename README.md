# README #

# AUTHOR: PEDRO FAVARI #

### COMPILE, EXECUTE THE TESTS AND RUN THE APPLICATION###

	mvn clean install package test spring-boot:run

### EXECUTING SUIT OF TESTS ONLY ###

	mvn test
	
### RUNNING APPLICATION ONLY ###

	mvn spring-boot:run
	
## API DOCUMENTATION ##

Create product
	- To create an product you need to call the URL, with the method, Content-Type and the body specified below: 

	URL: /api/avenuecode/products
	Method: POST
	Content-Type: application/json	
	Body:	{
				"name": "IPHONE 7",
				"description": "IPHONE 7 - 32GB",
				"parentProdId": 1
			}
		   
	Possible responses: 201, 500
	

Update product
	- To update an product you need to call the URL, with the method, Content-Type and the body specified below: 
	
	URL: /api/avenuecode/products
	Method: PUT
	Content-Type: application/json	
	Body:	{
				"id": 5,
				"name": "IPHONE 7",
				"description": "IPHONE 7 - 32GB",
				"parentProdId": 1
			}
		   
	Possible responses: 200, 404
	
	
Delete product
	- To delete an product you need to call the URL, with the method and id specified below: 
	
	URL: /api/avenuecode/products/{id}
	Method: DELETE
		   
	Possible responses: 200, 404
	
	
Get product without relationships
	- To get an product without relationship you need to call the URL, with the method and id specified below: 
	
	URL: /api/avenuecode/products/{id}
	Method: GET
		   
	Possible responses: 200, 404
	
	
Get product with child and/or image relationship
	- To get an product with child and/or image relationship, you need to call the URL, with the method and id specified below. 
		To specify if you want child relationship, set child with true for false, and same with image. 
	
	URL: /api/avenuecode/products/{id}?child=true&image=true
	Method: GET
		   
	Possible responses: 200, 404	
	
	
Get all products without relationships
	- To get all products without relationship you need to call the URL and method specified below: 
	
	URL: /api/avenuecode/products
	Method: GET
		   
	Possible responses: 200, 204
	
	
Get all product with child and/or image relationship
	- To get all product with child and/or image relationship, you need to call the URL and the method specified below. 
		To specify if you want child relationship, set child with true for false, and same with image. 
	
	URL: /api/avenuecode/products?child=true&image=true
	Method: GET
		   
	Possible responses: 200, 204
	
	
Get child products
	- To get an list of child products for and determined product you need to call the URL, with the method and id specified below: 
	
	URL: /api/avenuecode/products/childs/{id}
	Method: GET
		   
	Possible responses: 200, 404, 204
	
	
Get image from products
	- To get an list of images from products for and determined product you need to call the URL, 
		with the method and id specified below: 
	
	URL: /api/avenuecode/products/images/{id}
	Method: GET
		   
	Possible responses: 200, 404, 204
	
	
Create image
	- To create an image you need to call the URL, with the method, Content-Type and the body specified below: 

	URL: /api/avenuecode/images
	Method: POST
	Content-Type: application/json	
	Body:	{
    			"type": "BACK",
    			"productId": 2
			}
		   
	Possible responses: 201, 500
	

Update image
	- To update an image you need to call the URL, with the method, Content-Type and the body specified below: 
	
	URL: /api/avenuecode/images
	Method: PUT
	Content-Type: application/json	
	Body:	{
				"id": 1,
    			"type": "BACK",
    			"productId": 2
			}
		   
	Possible responses: 200, 404
	
	
Delete image
	- To image an product you need to call the URL, with the method and id specified below: 
	
	URL: /api/avenuecode/images/{id}
	Method: DELETE
		   
	Possible responses: 200, 404
	
	
	
	
	