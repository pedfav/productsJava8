package com.avenuecode.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avenuecode.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findProductByParentProdId(long parentProdId);
	
	Product findByName(String name);
}
