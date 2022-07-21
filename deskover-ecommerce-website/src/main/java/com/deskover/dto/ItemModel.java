package com.deskover.dto;

import com.deskover.entity.Discount;
import com.deskover.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemModel {
	
	public ItemModel(Product product) {
		this.name = product.getName();
		this.slug = product.getSlug();
		this.price = product.getPrice();
		this.img = product.getImg();
		this.imgUrl = product.getImgUrl();
		this.discount = product.getDiscount();
		this.category = product.getSubCategory().getCategory().getName();
		this.rating = product.getAverageRating();
	}
	
	private String name;
	private String slug;
	private Double price;
	private String img;
	private String imgUrl;
	private Discount discount;
	private String category;
	private int rating;
}
