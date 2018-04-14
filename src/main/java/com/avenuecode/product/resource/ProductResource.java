package com.avenuecode.product.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.model.Product;
import com.avenuecode.product.service.ProductService;

@Component
@Path("/products")
public class ProductResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductResource.class);
	
	private static final String ERROR_INSERT = "Can't insert this product, try again!";
	
	private static final String NOT_FOUND = "Product not found, try again!";
	
	private static final String DELETED = "Product with id=%s deleted!";
	
	@Autowired
	ProductService service;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product product) {
		LOG.info("Inserting product = {}", product.toString());
		
		try {
			service.save(product);
			return Response.status(Response.Status.CREATED).entity(product).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERROR_INSERT).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(Product product) {
		LOG.info("updating product with id = {}", product.getId());
		
		if(service.updateProduct(product)) {
			return Response.status(Response.Status.OK).entity(product).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteProduct(@PathParam("id") Long id) {
		LOG.info("Deleting product with id = {}", id);
		
		if(service.deleteProduct(id)) {
			return Response.status(Response.Status.OK).entity(String.format(DELETED, id.toString())).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("id") Long id,
								@QueryParam(value="child") @DefaultValue("false") boolean childs, 
								@QueryParam(value="image") @DefaultValue("false") boolean images) {
		LOG.info("Listing product with id ={}", id);
		LOG.info("childs = {}, images = {}", childs, images);
		
		Product product = service.getProduct(id, childs, images);
		
		if(product != null) {
			return Response.status(Response.Status.OK).entity(product).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProducts(@QueryParam(value="child") @DefaultValue("false") boolean childs, 
									@QueryParam(value="image") @DefaultValue("false") boolean images) {
		LOG.info("Listing all products...");
		LOG.info("childs = {}, images = {}", childs, images);
		
		List<Product> products = service.findAll(childs, images);
		
		if(products.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.OK).entity(products).build();
		}
	}
	
	@GET
	@Path("/childs/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildsForProduct(@PathParam("id") Long id) {
		LOG.info("Geting child products to product with id ={}", id);
		
		List<Product> products = service.getChildForProduct(id);
		
		if(products != null && !products.isEmpty()) {
			return Response.status(Response.Status.OK).entity(products).build();
		} else if (products == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}
	
	@GET
	@Path("/images/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImagesForProduct(@PathParam("id") Long id) {
		LOG.info("Geting images to product with id ={}", id);
		
		List<Image> images = service.getImagesForProduct(id);
		
		if(images != null && !images.isEmpty()) {
			return Response.status(Response.Status.OK).entity(images).build();
		} else if (images == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}
}
