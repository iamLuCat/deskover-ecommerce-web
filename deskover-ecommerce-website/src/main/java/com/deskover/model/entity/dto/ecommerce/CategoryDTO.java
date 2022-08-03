package com.deskover.model.entity.dto.ecommerce;

import java.util.ArrayList;
import java.util.List;

import com.deskover.model.entity.database.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
	public CategoryDTO(Category category) {
		this.name = category.getName();
		this.slug = category.getSlug();
		this.subcategories = new ArrayList<SubcategoryDTO>();
		category.getSubcategories().stream().forEach(subcategory ->{
			SubcategoryDTO subcategoryModel = new SubcategoryDTO(subcategory);
			subcategories.add(subcategoryModel);
		});
		this.img = category.getImg();
	}
	
	private String name;
	private String slug;
	private List<SubcategoryDTO> subcategories;
	private String img;
}
