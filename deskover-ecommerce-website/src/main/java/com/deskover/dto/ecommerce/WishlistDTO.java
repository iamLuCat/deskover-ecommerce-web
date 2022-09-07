package com.deskover.dto.ecommerce;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.deskover.entity.Wishlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistDTO {
	public WishlistDTO(Page<Wishlist> wishlist, Integer currentPage) {
		this.totalPage = wishlist.getTotalPages();
		this.items = wishlist.toList().stream().map(w -> new Item(w.getProduct())).collect(Collectors.toList());
		this.currentPage = currentPage;
	}
	
	private int totalPage;
	private int currentPage;
	private List<Item> items;
}
