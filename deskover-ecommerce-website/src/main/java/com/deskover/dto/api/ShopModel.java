package com.deskover.dto.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.deskover.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopModel {
	
	public ShopModel(Page<Product> items) {
		this.totalItems = items.getTotalElements();
		this.totalPage = items.getTotalPages() ;
		this.items = items.toList().stream().map(product -> new ItemModel(product)).collect(Collectors.toList());
	}
	
	private long totalPage;
	private long totalItems;
	
	List<ItemModel> items;
}
