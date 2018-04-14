package com.avenuecode.product.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.model.Product;
import com.avenuecode.product.service.ImageService;
import com.avenuecode.product.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceTest.class);
	
	private static final String name = "IPHONE TESTE";
	private static final String description = "IPHONE TESTE - 32GB";
	
	@Autowired
	ProductService service;
	
	@Autowired
	ImageService imageService;
	
	Product product = new Product();
	
	@Before
	public void initialize() {
		product.setName(name);
		product.setDescription(description);
	}
	
	@Test
	public void testSaveProduct() {
		LOG.info("initializing testSaveProduct...");
		service.save(product);
		
		Product prodCompare = service.GetByName(name);
		
		assertThat(product.getDescription()).isEqualTo(prodCompare.getDescription());
		
		service.deleteProduct(prodCompare.getId());
		LOG.info("testSaveProduct executed with success");
	}
	
	@Test
	public void testUpdateProduct() {
		LOG.info("initializing testUpdateProduct...");
		service.save(product);
		
		product.setDescription("NEW DESCRIPTION");
		
		service.updateProduct(product);
		
		Product prodCompare = service.GetByName(name);
		
		assertThat(product.getDescription()).isEqualTo(prodCompare.getDescription());
		
		service.deleteProduct(prodCompare.getId());
		LOG.info("testUpdateProduct executed with success");
	}
	
	@Test
	public void testDeleteProduct() {
		LOG.info("initializing testDeleteProduct...");
		service.save(product);
		
		Product prodCompare = service.GetByName(name);
		
		assertThat(service.deleteProduct(prodCompare.getId())).isTrue();
		LOG.info("testDeleteProduct executed with success");
	}
	
	@Test
	public void testGetProduct() {
		LOG.info("initializing testGetProduct...");
		service.save(product);
		
		Product prodCompare = service.GetByName(name);
		
		service.getProduct(prodCompare.getId(), false, false);
		
		assertThat(service.getProduct(prodCompare.getId(), false, false).getName()).isEqualTo(product.getName());
		service.deleteProduct(prodCompare.getId());
		LOG.info("testGetProduct executed with success");
	}
	
	@Test
	public void testFindAll() {
		LOG.info("initializing testFindAll...");
		service.save(product);
		
		Product prodCompare = service.GetByName(name);
		
		service.findAll(false, false);
		
		assertThat(service.findAll(false, false)).isNotEmpty();
		service.deleteProduct(prodCompare.getId());
		LOG.info("testFindAll executed with success");
	}
	
	@Test
	public void testGetChild() {
		LOG.info("initializing testGetChild...");
		service.save(product);
		
		Product prodCompare = service.GetByName(name);
		
		Product childProduct = new Product();
		
		childProduct.setName("IPHONE CHILD");
		childProduct.setDescription("TESTE");
		childProduct.setParentProdId(prodCompare.getId());
		service.save(childProduct);
		
		assertThat(service.getChildForProduct(prodCompare.getId()).get(0).getName()).isEqualTo(childProduct.getName());
		service.deleteProduct(prodCompare.getId());
		service.deleteProduct(childProduct.getId());
		LOG.info("testGetChild executed with success");
	}
	
	@Test
	public void testGetImage() {
		LOG.info("initializing testGetImage...");
		service.save(product);
		
		Product prodCompare = service.GetByName(name);
		
		Image image = new Image();
		image.setProductId(prodCompare.getId());
		image.setType("BACK");
		imageService.save(image);
		
		assertThat(service.getImagesForProduct(prodCompare.getId()).get(0).getType()).isEqualTo(image.getType());
		imageService.deleteImage(service.getImagesForProduct(prodCompare.getId()).get(0).getId());
		service.deleteProduct(prodCompare.getId());
		LOG.info("testGetImage executed with success");
	}
}
