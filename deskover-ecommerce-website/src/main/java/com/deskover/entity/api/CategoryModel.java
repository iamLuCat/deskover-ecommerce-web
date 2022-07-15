package com.deskover.entity.api;

import java.util.ArrayList;
import java.util.List;

import com.deskover.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryModel {
	public CategoryModel(Category category) {
		this.name = category.getName();
		this.slug = category.getSlug();
		this.subcategories = new ArrayList<SubcategoryModel>();
		category.getSubcategories().stream().forEach(subcategory ->{
			SubcategoryModel subcategoryModel = new SubcategoryModel(subcategory);
			subcategories.add(subcategoryModel);
		});
	}
	
	private String name;
	private String slug;
	private List<SubcategoryModel> subcategories;
	
}
