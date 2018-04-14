package com.avenuecode.product.test.resource;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.model.Product;
import com.avenuecode.product.resource.ProductResource;
import com.avenuecode.product.service.ImageService;
import com.avenuecode.product.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductResourceTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductResourceTest.class);
	
	@LocalServerPort
	private int port;
	
	private static final String ERROR_INSERT = "Can't insert this product, try again!";
	
	private static final String url = "/api/avenuecode/products";
	
	private static final String name = "IPHONE TEST UPDATE";
	private static final String description = "IPHONE TEST SERVICE";
	private static final Long parentProdId = 100L;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ProductService prodService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ProductResource resource;
	
	Product product = new Product();
	
	@Before
	public void setUp() throws Exception {
		product.setName(name);
		product.setDescription(description);
		product.setParentProdId(parentProdId);
	}
	
	@Test
	public void testAddProductCreated() {
		LOG.info("Testing testAddProductCreated...");
		LOG.info("testAddProductCreated : searching URL {}", url);
		ResponseEntity<Product> response = this.restTemplate.postForEntity(url, product, Product.class);
		LOG.info("testAddProductCreated: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getDescription()).isEqualTo(product.getDescription());
		prodService.deleteProduct(response.getBody().getId());
		LOG.info("testAddProductCreated executed with success");
	}
	
	@Test
	public void testAddProductError() {
		LOG.info("Testing testAddProductError...");
		LOG.info("testAddProductError : searching URL {}", url);
		product.setName(null);
		ResponseEntity<String> response = this.restTemplate.postForEntity(url, product, String.class);
		LOG.info("testAddProductError: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(response.getBody()).isEqualTo(ERROR_INSERT);
		product.setName(name);
		LOG.info("testAddProductError executed with success");
	}
	
	@Test
	public void testUpdatedProductOk() {
		LOG.info("Testing testUpdatedProductOk...");
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		prodService.save(product);
        
		Product prodUpdate = prodService.GetByName(product.getName());
		prodUpdate.setName("NEW NAME");
        
		WebTarget target = client.target("http://localhost:" + port + "/api/avenuecode");
		Response response = target.path("/products").request().put(Entity.json(prodUpdate));
		LOG.info("testUpdatedProductOk: " + response);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		prodService.deleteProduct(prodUpdate.getId());
		LOG.info("testUpdatedProductOk executed with success");
	}
	
	@Test
	public void testUpdatedProductNOk() {
		LOG.info("Testing testUpdatedProductNOk...");
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
        
		Product productUpdate = new Product();
		productUpdate.setId(0L);
        
		WebTarget target = client.target("http://localhost:" + port + "/api/avenuecode");
		Response response = target.path("/products").request().put(Entity.json(productUpdate));
		LOG.info("testUpdatedProductNOk: " + response);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		LOG.info("testUpdatedProductNOk executed with success");
	}
	
	@Test
	public void testDeleteProduct() {
		LOG.info("Testing testDeleteProduct...");
		prodService.save(product);
        
		Product prodDelete = prodService.GetByName(product.getName());
		
		Response response = resource.deleteProduct(prodDelete.getId());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		prodService.deleteProduct(prodDelete.getId());
		LOG.info("testDeleteProduct executed with success");
	}
	
	@Test
	public void testDeleteProductNOK() {
		LOG.info("Testing testDeleteProductNOK...");
		
		Response response = resource.deleteProduct(0L);
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		LOG.info("testDeleteProductNOK executed with success");
	}
	
	@Test
	public void testGetProductOK() {
		LOG.info("Testing testGetProductOK...");
		LOG.info("testGetProductOK : searching URL {}", url);
		prodService.save(product);
        
		Product prodGet = prodService.GetByName(product.getName());
		
		ResponseEntity<Product> response = this.restTemplate.getForEntity(url + "/" + prodGet.getId(), Product.class);
		LOG.info("testGetProductOK: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getDescription()).isEqualTo(product.getDescription());
		prodService.deleteProduct(response.getBody().getId());
		LOG.info("testGetProductOK executed with success");
	}
	
	@Test
	public void testGetProductNOK() {
		LOG.info("Testing testGetProductNOK...");
		LOG.info("testGetProductNOK : searching URL {}", url);
        
		ResponseEntity<String> response = this.restTemplate.getForEntity(url + "/0", String.class);
		LOG.info("testGetProductNOK: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		LOG.info("testGetProductNOK executed with success");
	}
	
	@Test
	public void testGetAllProducstOK() {
		LOG.info("Testing testGetAllProducstOK...");
		LOG.info("testGetAllProducstOK : searching URL {}", url);
		prodService.save(product);
        
		Product prodGetAll = prodService.GetByName(product.getName());
		
		ResponseEntity<Object[]> response = this.restTemplate.getForEntity(url, Object[].class);
		LOG.info("testGetAllProducstOK: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		prodService.deleteProduct(prodGetAll.getId());
		LOG.info("testGetAllProducstOK executed with success");
	}
	
	@Test
	public void testGetAllChildProducstOK() {
		LOG.info("Testing testGetAllChildProducstOK...");
		LOG.info("testGetAllChildProducstOK : searching URL {}", url);
		prodService.save(product);
		Product prodGetAll = prodService.GetByName(product.getName());
		
		Product child = new Product();
		child.setName("CHILD");
		child.setDescription("CHILD");
		child.setParentProdId(prodGetAll.getId());
		prodService.save(child);
		
		ResponseEntity<Object[]> response = this.restTemplate.getForEntity(url + "/childs/" + prodGetAll.getId(), Object[].class);
		LOG.info("testGetAllChildProducstOK: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		prodService.deleteProduct(prodGetAll.getId());
		LOG.info("testGetAllChildProducstOK executed with success");
	}
	
	@Test
	public void testGetNoChildProducst() {
		LOG.info("Testing testGetNoChildProducst...");
		LOG.info("testGetNoChildProducst : searching URL {}", url);
		prodService.save(product);
		Product prodGetAll = prodService.GetByName(product.getName());
		
		ResponseEntity<Object[]> response = this.restTemplate.getForEntity(url + "/childs/" + prodGetAll.getId(), Object[].class);
		LOG.info("testGetNoChildProducst: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		prodService.deleteProduct(prodGetAll.getId());
		LOG.info("testGetNoChildProducst executed with success");
	}
	
	@Test
	public void testGetAllImagesProducstOK() {
		LOG.info("Testing testGetAllImagesProducstOK...");
		LOG.info("testGetAllImagesProducstOK : searching URL {}", url);
		prodService.save(product);
		Product prodGetAll = prodService.GetByName(product.getName());
		
		Image image = new Image();
		image.setProductId(prodGetAll.getId());
		image.setType("TEST");
		imageService.save(image);
		
		ResponseEntity<Object[]> response = this.restTemplate.getForEntity(url + "/images/" + prodGetAll.getId(), Object[].class);
		LOG.info("testGetAllImagesProducstOK: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		prodService.deleteProduct(prodGetAll.getId());
		LOG.info("testGetAllImagesProducstOK executed with success");
	}
	
	@Test
	public void testGetNoImagesProducstOK() {
		LOG.info("Testing testGetNoImagesProducstOK...");
		LOG.info("testGetNoImagesProducstOK : searching URL {}", url);
		prodService.save(product);
		Product prodGetAll = prodService.GetByName(product.getName());
		
		ResponseEntity<Object[]> response = this.restTemplate.getForEntity(url + "/images/" + prodGetAll.getId(), Object[].class);
		LOG.info("testGetNoImagesProducstOK: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		prodService.deleteProduct(prodGetAll.getId());
		LOG.info("testGetNoImagesProducstOK executed with success");
	}
}
