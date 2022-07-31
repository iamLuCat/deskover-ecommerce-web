package com.deskover.service;

import java.util.List;

import com.deskover.model.entity.database.Province;

public interface ProvinceService {
	List<Province> getAll();

	Province getById(Long id);
}
