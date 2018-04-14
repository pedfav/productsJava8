package com.avenuecode.product.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String description;
	
	@Column(nullable=true)
	private Long parentProdId;
	
	@Transient
	private List<Image> images;
	
	@Transient
	private List<Product> childProducts;

	public Product() {
		super();
	}

	public Product(Long id, String name, String description, Long parentProdId, List<Image> images,
			List<Product> childProducts) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentProdId = parentProdId;
		this.images = images;
		this.childProducts = childProducts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParentProdId() {
		return parentProdId;
	}

	public void setParentProdId(Long parentProdId) {
		this.parentProdId = parentProdId;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Product> getChildProducts() {
		return childProducts;
	}

	public void setChildProducts(List<Product> childProducts) {
		this.childProducts = childProducts;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", parentProdId="
				+ parentProdId + ", images=" + images + ", childProducts=" + childProducts + "]";
	}
}
