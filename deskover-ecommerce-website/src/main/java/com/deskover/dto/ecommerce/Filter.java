package com.deskover.dto.ecommerce;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
	private String keyword;
	private String category;
	private String subcategory;
	private List<String> brands;
	private Double minPrice;
	private Double maxPrice;
	private int currentPage;
	private int itemsPerPage;
	private String sort;
}
