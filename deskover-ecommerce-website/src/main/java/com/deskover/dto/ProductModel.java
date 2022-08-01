package com.deskover.dto;

import com.deskover.entity.Product;
import com.deskover.entity.ProductThumbnail;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductModel {
	
	public ProductModel(Product product) {
		this.name = product.getName();
		this.slug = product.getSlug();
		this.price = product.getPrice();
		this.img = product.getImg();
		this.imgUrl = product.getImgUrl();
		this.video = product.getVideo();
		this.brand = product.getBrand().getName();
		this.brand_slug = product.getBrand().getSlug();
		this.subcategory = product.getSubCategory().getName();
		this.subcategory_slug = product.getSubCategory().getSlug();
		this.category = product.getSubCategory().getCategory().getName();
		this.category_slug = product.getSubCategory().getCategory().getSlug();
		this.design = product.getDesign();
		this.spec = product.getSpec();
		this.utility = product.getUtility();
		this.other = product.getOther();
		this.thumbsnails = product.getProductThumbnails().stream().map(ProductThumbnail::getThumbnailUrl).collect(Collectors.toList());
		this.averageRating = product.getAverageRating();
		this.totalRating = product.getTotalRating();
		this.item = new ItemModel(product);
	}
	
	private String name;
	private String slug;
	private Double price;
	private String img;
	private String imgUrl;
	private String video;
	private String brand;
	private String brand_slug;
	private String subcategory;
	private String subcategory_slug;
	private String category;
	private String category_slug;
	private String design;
	private String spec;
	private String utility;
	private String other;
	private List<String> thumbsnails;
	private Integer averageRating; 
	private Integer totalRating;
	private ItemModel item;
}
