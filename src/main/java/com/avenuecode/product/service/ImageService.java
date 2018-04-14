package com.avenuecode.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.repository.ImageRepository;

@Service
public class ImageService implements IImageService{
	
	@Autowired
	ImageRepository repositoryImage;

	@Override
	public void save(Image image) {
		repositoryImage.save(image);
	}

	@Override
	public boolean updateImage(Image image) {
		if (repositoryImage.exists(image.getId())) {
			repositoryImage.save(image);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteImage(Long id) {
		if (repositoryImage.exists(id)) {
			repositoryImage.delete(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Image getImageByType(String type) {
		return repositoryImage.findImageByType(type);
	}
}
