package com.deskover.dto.ecommerce;

import com.deskover.entity.Brand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDTO {
	public BrandDTO(Brand brand) {
		this.name = brand.getName();
		this.slug = brand.getSlug();
		this.imgUrl = brand.getImg();
	}
	
	private String name;
	private String slug;
	private String imgUrl;
}
