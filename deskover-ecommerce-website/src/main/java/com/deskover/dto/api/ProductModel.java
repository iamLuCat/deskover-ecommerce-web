package com.deskover.dto.api;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.deskover.entity.Product;
import com.deskover.entity.ProductThumbnail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {
	
	public ProductModel(Product product) {
		this.name = product.getName();
		this.slug = product.getSlug();
		this.price = product.getPrice();
		this.image = product.getImage();
		this.video = product.getVideo();
		this.spec = product.getSpec();
		this.brand = product.getBrand().getName();
		this.brand_slug = product.getBrand().getSlug();
		this.subcategory = product.getSubCategory().getName();
		this.subcategory_slug = product.getSubCategory().getSlug();
		this.category = product.getSubCategory().getCategory().getName();
		this.category_slug = product.getSubCategory().getCategory().getSlug();
		
		this.thumbsnails = product.getProductThumbnails().stream().map(ProductThumbnail::getThumbnail).collect(Collectors.toList());
		this.averageRating = product.getAverageRating();
		this.totalRating = product.getTotalRating();
	}
	
	private String name;
	private String slug;
	private Double price;
	private String image;
	private String video;
	private String spec;
	private String brand;
	private String brand_slug;
	private String subcategory;
	private String subcategory_slug;
	private String category;
	private String category_slug;
	
	private List<String> thumbsnails;
	private Integer averageRating; 
	private Integer totalRating;
}
