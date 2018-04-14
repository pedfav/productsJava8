package com.avenuecode.product.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.service.ImageService;

@Component
@Path("/images")
public class ImageResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImageResource.class);
	
	private static final String ERROR_INSERT = "Can't insert this image, try again!";
	
	private static final String NOT_FOUND = "Image not found, try again!";
	
	private static final String DELETED = "Image with id=%s deleted!";
	
	@Autowired
	ImageService service;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addImage(Image image) {
		LOG.info("Inserting image = {}", image.toString());
		
		try {
			service.save(image);
			return Response.status(Response.Status.CREATED).entity(image).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERROR_INSERT).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateImage(Image image) {
		LOG.info("Updating image with id = {}", image.getId());
		
		if(service.updateImage(image)) {
			return Response.status(Response.Status.OK).entity(image).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteImage(@PathParam("id") Long id) {
		LOG.info("Deleting product with id = {}", id);
		
		if(service.deleteImage(id)) {
			return Response.status(Response.Status.OK).entity(String.format(DELETED, id.toString())).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(NOT_FOUND).build();
		}
	}
}
