package com.deskover.entity.api;

import java.util.List;

import com.deskover.entity.Category;
import com.deskover.entity.Subcategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubcategoryModel {

	public SubcategoryModel(Subcategory subcategory) {
		this.name = subcategory.getName();
		this.slug = subcategory.getSlug();
	}
	
	private String name;
	private String slug;	
}
