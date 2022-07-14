package com.deskover.service;

import com.deskover.entity.api.FilterModel;
import com.deskover.entity.api.ShopModel;

public interface ShopService {
	public ShopModel search(FilterModel filter);
}
