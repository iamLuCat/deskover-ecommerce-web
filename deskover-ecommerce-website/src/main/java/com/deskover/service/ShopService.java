package com.deskover.service;

import com.deskover.dto.FilterModel;
import com.deskover.dto.ProductModel;
import com.deskover.dto.ShopModel;

public interface ShopService {
	public ShopModel search(FilterModel filter);
	public ProductModel getProduct(String slug);
}
