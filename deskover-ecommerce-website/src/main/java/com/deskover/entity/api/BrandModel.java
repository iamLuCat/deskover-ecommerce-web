package com.deskover.entity.api;

import com.deskover.entity.Brand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandModel {
	public BrandModel(Brand brand) {
		this.name = brand.getName();
		this.slug = brand.getSlug();
	}
	
	private String name;
	private String slug;
}
