package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.District;
import com.deskover.repository.DistrictReponsitory;
import com.deskover.service.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService{

	@Autowired 
	DistrictReponsitory repo;
	
	@Override
	public List<District> getByProvinceId(Long provinceId) {
		return repo.findByProvinceId(provinceId);
	}

	@Override
	public District getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	

}
