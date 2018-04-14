package com.avenuecode.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avenuecode.product.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
	List<Image> findImageByProductId(long productId);
	
	Image findImageByType(String type);
}
