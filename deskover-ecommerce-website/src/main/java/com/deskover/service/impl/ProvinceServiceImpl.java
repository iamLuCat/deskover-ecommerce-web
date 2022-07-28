package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Province;
import com.deskover.repository.ProvinceReponsitory;
import com.deskover.service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService{
	@Autowired 
	ProvinceReponsitory repo;
	
	@Override
	public List<Province> getAll() {
		return repo.findAll();
	}

	@Override
	public Province getById(Long id) {
		return repo.findById(id).orElse(null);
	}

}
