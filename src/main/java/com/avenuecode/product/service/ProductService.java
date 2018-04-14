package com.avenuecode.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.product.model.Image;
import com.avenuecode.product.model.Product;
import com.avenuecode.product.repository.ImageRepository;
import com.avenuecode.product.repository.ProductRepository;

@Service
public class ProductService implements IProductService {

	@Autowired
	ProductRepository repositoryProd;

	@Autowired
	ImageRepository repositoryImage;

	@Override
	public void save(Product product) {
		repositoryProd.save(product);
	}

	@Override
	public boolean updateProduct(Product product) {
		if (repositoryProd.exists(product.getId())) {
			repositoryProd.save(product);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteProduct(Long id) {
		if (repositoryProd.exists(id)) {
			List<Product> products = repositoryProd.findProductByParentProdId(id);

			for (Product product : products) {
				product.setParentProdId(null);
			}

			repositoryProd.save(products);

			repositoryProd.delete(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Product getProduct(Long id, boolean child, boolean image) {

		if (repositoryProd.exists(id)) {

			Product product = repositoryProd.findOne(id);

			if (child) {
				product.setChildProducts(repositoryProd.findProductByParentProdId(id));
			}
			if (image) {
				product.setImages(repositoryImage.findImageByProductId(id));
			}

			return product;
		} else {
			return null;
		}
	}

	@Override
	public List<Product> findAll(boolean child, boolean image) {

		List<Product> products = repositoryProd.findAll();

		if (child || image) {
			for (Product product : products) {
				if (child) {
					product.setChildProducts(repositoryProd.findProductByParentProdId(product.getId()));
				}
				if (image) {
					product.setImages(repositoryImage.findImageByProductId(product.getId()));
				}
			}
		}
		return products;
	}

	@Override
	public List<Product> getChildForProduct(Long id) {
		if (repositoryProd.exists(id)) {
			List<Product> products = repositoryProd.findProductByParentProdId(id);
			return products;
		} else {
			return null;
		}
	}

	@Override
	public List<Image> getImagesForProduct(Long id) {
		if (repositoryProd.exists(id)) {
			List<Image> images = repositoryImage.findImageByProductId(id);
			return images;
		} else {
			return null;
		}
	}

	@Override
	public Product GetByName(String name) {
		return repositoryProd.findByName(name);
	}
}
