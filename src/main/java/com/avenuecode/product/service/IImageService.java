package com.avenuecode.product.service;

import com.avenuecode.product.model.Image;

public interface IImageService {
	
	void save(Image image);
	
	boolean updateImage(Image image);
	
	boolean deleteImage(Long id);
	
	Image getImageByType(String type);
}
