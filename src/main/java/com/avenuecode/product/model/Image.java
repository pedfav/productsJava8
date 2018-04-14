package com.avenuecode.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String type;
	
	@Column(nullable=false)
	private Long productId;
	
	public Image() {
		super();
	}
	
	public Image(long id, String type, Long productId) {
		super();
		this.id = id;
		this.type = type;
		this.productId = productId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Override
	public String toString() {
		return "Image [id=" + id + ", type=" + type + ", productId=" + productId + "]";
	}
}
