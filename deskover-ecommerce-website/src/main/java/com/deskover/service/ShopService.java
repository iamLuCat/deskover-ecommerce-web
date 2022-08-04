package com.deskover.service;

import com.deskover.model.entity.dto.ecommerce.Filter;
import com.deskover.model.entity.dto.ecommerce.ProductDTO;
import com.deskover.model.entity.dto.ecommerce.Shop;

public interface ShopService {
	public Shop search(Filter filter);
	public ProductDTO getProduct(String slug);
}
