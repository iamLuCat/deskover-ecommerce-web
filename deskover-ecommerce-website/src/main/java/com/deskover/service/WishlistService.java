package com.deskover.service;

import java.util.List;

public interface WishlistService {
	List<String> setWishlist(String slug, String name);

	List<String> getWishlist(String name);
}
