package com.avenuecode.product.service;

import java.util.List;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.model.Product;

public interface IProductService {
	
	void save(Product product);
	
	boolean updateProduct(Product product);
	
	boolean deleteProduct(Long id);
	
	Product getProduct(Long id, boolean child, boolean image);
	
	List<Product> findAll(boolean child, boolean image);
	
	List<Product> getChildForProduct(Long id);
	
	List<Image> getImagesForProduct(Long id);
	
	Product GetByName(String name);
}
