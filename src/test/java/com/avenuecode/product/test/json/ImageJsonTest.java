package com.avenuecode.product.test.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.product.model.Image;

@RunWith(SpringRunner.class)
@JsonTest
public class ImageJsonTest {
	
	private final static Logger LOG = LoggerFactory.getLogger(ImageJsonTest.class);
	
    private final static String IMAGE_JSON = "{\"id\": 1,\"type\": \"FRONT\",\"productId\": 2}";
    
    private static final Image image = new Image();
    
    @Autowired
    private JacksonTester<Image> jsonTester;
    
    static {
    	image.setId(1L);
    	image.setType("FRONT");
    	image.setProductId(2L);
    }
    
    @Test
    public void testImageSerialize() throws Exception {
    	LOG.info("Image test serialize: {}", this.jsonTester.write(image));
        assertThat(this.jsonTester.write(image)).isEqualToJson(IMAGE_JSON);
        assertThat(this.jsonTester.write(image)).hasJsonPathNumberValue("@.id");
		assertThat(this.jsonTester.write(image)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    }
    
    @Test
	public void testImageDeserialize() throws Exception {
		LOG.info("Parsed image: " + this.jsonTester.parse(IMAGE_JSON).getObject());
		assertThat(this.jsonTester.parse(IMAGE_JSON).getObject()).isEqualToComparingFieldByField(image);
		assertThat(this.jsonTester.parseObject(IMAGE_JSON).getId()).isEqualTo(1L);
	}
}
