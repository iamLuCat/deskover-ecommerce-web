package com.deskover.dto.ecommerce;

import java.sql.Timestamp;
import java.util.Objects;

import com.deskover.entity.Discount;
import com.deskover.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSaleDTO {
	
	public ProductSaleDTO(Product product) {
		this.name = product.getName();
		this.slug = product.getSlug();
		this.price = product.getPrice();
		this.imgUrl = product.getImg();
		this.discount = product.getDiscount();
		this.category = product.getSubCategory().getCategory().getName();
		this.rating = product.getAverageRating();
		this.endDate = product.getFlashSale().getEndDate();
		this.price_sale = product.getPrice() - (product.getPrice() * product.getDiscount().getPercent()/100);
	}
	
	private String name;
	private String slug;
	private Double price;
	private Double price_sale;
	private String imgUrl;
	private Discount discount;
	private String category;
	private Timestamp endDate;
	private int rating;
	private boolean sale  = true;
}
