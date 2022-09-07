package com.deskover.dto.ecommerce;

import java.util.List;

import com.deskover.entity.Category;
import com.deskover.entity.Subcategory;

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
