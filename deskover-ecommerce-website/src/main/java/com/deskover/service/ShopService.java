package com.deskover.service;

import com.deskover.dto.api.FilterModel;
import com.deskover.dto.api.ProductModel;
import com.deskover.dto.api.ShopModel;

public interface ShopService {
	public ShopModel search(FilterModel filter);

	public ProductModel getProduct(String slug);
}
