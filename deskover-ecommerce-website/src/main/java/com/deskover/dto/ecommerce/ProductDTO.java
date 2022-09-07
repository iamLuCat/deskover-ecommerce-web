package com.deskover.dto.ecommerce;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.deskover.entity.Product;
import com.deskover.entity.ProductThumbnail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	
	public ProductDTO(Product product) {
		this.name = product.getName();
		this.slug = product.getSlug();
		this.price = product.getPrice();
		this.price_sale = product.getPriceSale();
		this.img = product.getImg();
		this.imgUrl = product.getImg();
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
		this.thumbsnails = product.getProductThumbnails().stream().map(ProductThumbnail::getThumbnail).collect(Collectors.toList());
		this.averageRating = product.getAverageRating();
		this.totalRating = product.getTotalRating();
		this.description = product.getDescription();
		
		this.countRating = new Integer[]{
				product.getRating1(),
				product.getRating2(),
				product.getRating3(),
				product.getRating4(),
				product.getRating5(),
				};  

		System.out.println(this.countRating.toString());
		
		this.item = new Item(product);
		
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
	private Integer[] countRating;
	private Item item;
	private String description;
	private boolean sale = false;
}
