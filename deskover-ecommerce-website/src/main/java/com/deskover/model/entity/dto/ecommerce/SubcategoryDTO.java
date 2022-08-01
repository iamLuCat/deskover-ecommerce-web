package com.deskover.model.entity.dto.ecommerce;

import java.util.List;

import com.deskover.model.entity.database.Category;
import com.deskover.model.entity.database.Subcategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubcategoryDTO {

	public SubcategoryDTO(Subcategory subcategory) {
		this.name = subcategory.getName();
		this.slug = subcategory.getSlug();
	}
	
	private String name;
	private String slug;	
}
