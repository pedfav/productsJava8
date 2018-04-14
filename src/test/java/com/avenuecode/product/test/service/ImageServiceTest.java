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
import com.avenuecode.product.service.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImageServiceTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImageServiceTest.class);
	
	private static final String type = "TEST TYPE";
	private static final Long productId = 101L;
	
	private static final String UPDATED_TYPE = "new type";
	
	@Autowired
	ImageService service;
	
	Image image = new Image();
	
	@Before
	public void initialize() {
		image.setType(type);
		image.setProductId(productId);
	}
	
	@Test
	public void testSaveImage() {
		LOG.info("initializing testSaveImage...");
		service.save(image);
		
		Image imageCompare = service.getImageByType(type);
		
		assertThat(image.getType()).isEqualTo(imageCompare.getType());
		
		service.deleteImage(imageCompare.getId());
		LOG.info("testSaveImage executed with success");
	}
	
	@Test
	public void testUpdateImage() {
		LOG.info("initializing testUpdateImage...");
		service.save(image);
		
		image.setType(UPDATED_TYPE);
		
		service.updateImage(image);
		
		Image imageCompare = service.getImageByType(UPDATED_TYPE);
		
		assertThat(image.getType()).isEqualTo(imageCompare.getType());
		
		service.deleteImage(imageCompare.getId());
		LOG.info("testUpdateImage executed with success");
	}
	
	@Test
	public void testDeleteImage() {
		LOG.info("initializing testDeleteImage...");
		service.save(image);
		
		Image imageCompare = service.getImageByType(type);
		
		assertThat(service.deleteImage(imageCompare.getId())).isTrue();
		LOG.info("testDeleteImage executed with success");
	}

}
