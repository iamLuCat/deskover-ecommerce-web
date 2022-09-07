package com.deskover.dto.ecommerce;

import java.sql.Timestamp;
import java.util.Objects;

import com.deskover.entity.Discount;
import com.deskover.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
	
	public Item(Product product) {
		this.name = product.getName();
		this.slug = product.getSlug();
		this.price = product.getPrice();
		this.imgUrl = product.getImg();
		this.discount = product.getDiscount();
		this.category = product.getSubCategory().getCategory().getName();
		this.rating = product.getAverageRating();
	
		this.price_sale = product.getPriceSale();

		if(Objects.isNull(product.getDiscount()))
			return;
		Timestamp current = new Timestamp(System.currentTimeMillis());
		if((!Objects.isNull(this.price_sale)) && (this.price_sale > 0)
				&& (product.getDiscount().getStartDate().compareTo(current)<= 0) 
				&& (product.getDiscount().getEndDate().compareTo(current)>= 0))
			this.sale = true;
	}
	
	private String name;
	private String slug;
	private Double price;
	private Double price_sale;
	private String imgUrl;
	private Discount discount;
	private String category;
	private int rating;
	private boolean sale  = false;
}
