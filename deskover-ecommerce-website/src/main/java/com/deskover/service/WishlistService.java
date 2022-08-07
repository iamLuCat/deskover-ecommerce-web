package com.deskover.service;

import java.util.List;

import com.deskover.model.entity.dto.ecommerce.WishlistDTO;

public interface WishlistService {
	List<String> setWishlist(String slug, String name);

	List<String> getWishlist(String name);

	WishlistDTO getWishlist(String name, Integer p);
}
