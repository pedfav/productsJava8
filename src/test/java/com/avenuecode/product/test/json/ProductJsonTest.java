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

import com.avenuecode.product.model.Product;

@RunWith(SpringRunner.class)
@JsonTest
public class ProductJsonTest {

	private final static Logger LOG = LoggerFactory.getLogger(ProductJsonTest.class);

	private final static String PRODUCT_JSON = "{\"id\": 2,\"name\": \"IPHONE 7\",\"description\": \"IPHONE 7 - 32GB\","
			+ "\"parentProdId\": 1}";

	private static final Product product = new Product();

	@Autowired
	private JacksonTester<Product> jsonTester;

	static {
		product.setId(2L);
		product.setName("IPHONE 7");
		product.setDescription("IPHONE 7 - 32GB");
		product.setParentProdId(1L);
	}

	@Test
	public void testProductSerialize() throws Exception {
		LOG.info("Product test serialize: {}", this.jsonTester.write(product));
		assertThat(this.jsonTester.write(product)).isEqualToJson(PRODUCT_JSON);
		assertThat(this.jsonTester.write(product)).hasJsonPathNumberValue("@.id");
		assertThat(this.jsonTester.write(product)).extractingJsonPathNumberValue("@.id").isEqualTo(2);
	}

	@Test
	public void testProductDeserialize() throws Exception {
		LOG.info("Parsed product: " + this.jsonTester.parse(PRODUCT_JSON).getObject());
		assertThat(this.jsonTester.parse(PRODUCT_JSON).getObject()).isEqualToIgnoringGivenFields(product, "images","childProducts");
		assertThat(this.jsonTester.parseObject(PRODUCT_JSON).getId()).isEqualTo(2L);
	}
}
