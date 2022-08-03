package com.deskover.model.entity.dto.ecommerce;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.deskover.model.entity.database.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {
	
	public Shop(Page<Product> items) {
		this.totalItems = items.getTotalElements();
		this.totalPage = items.getTotalPages() ;
		this.items = items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}
	
	private long totalPage;
	private long totalItems;
	
	List<Item> items;
}
