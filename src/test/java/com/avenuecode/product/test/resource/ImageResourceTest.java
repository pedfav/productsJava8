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
import com.avenuecode.product.resource.ImageResource;
import com.avenuecode.product.service.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImageResourceTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImageResourceTest.class);
	
	private static final String url = "/api/avenuecode/images";
	
	private static final Long productId = 200L;
	private static final String type = "TYPE TEST";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ImageResource resource;
	
	Image image = new Image();
	
	@Before
	public void setUp() throws Exception {
		image.setProductId(productId);
		image.setType(type);
	}
	
	@Test
	public void testAddImageCreated() {
		LOG.info("Testing testAddImageCreated...");
		LOG.info("testAddImageCreated : searching URL {}", url);
		ResponseEntity<Image> response = this.restTemplate.postForEntity(url, image, Image.class);
		LOG.info("testAddImageCreated: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getType()).isEqualTo(image.getType());
		imageService.deleteImage(response.getBody().getId());
		LOG.info("testAddImageCreated executed with success");
	}
	
	@Test
	public void testAddImageError() {
		LOG.info("Testing testAddImageError...");
		LOG.info("testAddImageError : searching URL {}", url);
		image.setType(null);
		ResponseEntity<String> response = this.restTemplate.postForEntity(url, image, String.class);
		LOG.info("testAddImageError: " + response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		image.setType(type);
		LOG.info("testAddImageError executed with success");
	}
	
	@Test
	public void testUpdatedImageOk() {
		LOG.info("Testing testUpdatedImageOk...");
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		imageService.save(image);        
		Image imageUpdate = imageService.getImageByType(type);
		imageUpdate.setType("NEW TYPE");
        
		WebTarget target = client.target("http://localhost:" + port + "/api/avenuecode");
		Response response = target.path("/images").request().put(Entity.json(imageUpdate));
		LOG.info("testUpdatedImageOk: " + response);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		imageService.deleteImage(imageUpdate.getId());
		LOG.info("testUpdatedImageOk executed with success");
	}
	
	@Test
	public void testUpdatedImageError() {
		LOG.info("Testing testUpdatedImageError...");
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		Image imageUpdate = new Image();
		imageUpdate.setId(0L);
        
		WebTarget target = client.target("http://localhost:" + port + "/api/avenuecode");
		Response response = target.path("/images").request().put(Entity.json(imageUpdate));
		LOG.info("testUpdatedImageError: " + response);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		LOG.info("testUpdatedImageError executed with success");
	}
	
	@Test
	public void testDeleteImage() {
		LOG.info("Testing testDeleteImage...");
		imageService.save(image);
        
		Image imageDelete = imageService.getImageByType(image.getType());
		
		Response response = resource.deleteImage(imageDelete.getId());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		imageService.deleteImage(image.getId());
		LOG.info("testDeleteImage executed with success");
	}
	
	@Test
	public void testDeleteImageNOK() {
		LOG.info("Testing testDeleteImageNOK...");
		
		Response response = resource.deleteImage(0L);
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		LOG.info("testDeleteImageNOK executed with success");
	}
}
