package com.deskover.model.entity.dto.ecommerce;

import com.deskover.model.entity.database.Brand;

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
