package com.avenuecode.product.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

import com.avenuecode.product.resource.ImageResource;
import com.avenuecode.product.resource.ProductResource;

@Component
@ApplicationPath("/api/avenuecode")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		
		register(ProductResource.class);
		register(ImageResource.class);
		register(WadlResource.class);
	}
}