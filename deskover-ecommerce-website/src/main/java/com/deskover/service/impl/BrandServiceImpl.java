package com.deskover.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Brand;
import com.deskover.repository.BrandRepository;
import com.deskover.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandRepository repo;

	@Override
	public List<Brand> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public List<Brand> getAllBrandIsActived() {
		// TODO Auto-generated method stub
		return repo.findByActived(Boolean.TRUE);
	}

	@Override
	public Brand get(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	@Override
	public Brand get(String slug) {
		// TODO Auto-generated method stub
		return repo.findBySlug(slug);
	}

	@Override
	public Boolean existsBySlug(String slug) {
		// TODO Auto-generated method stub
		return repo.existsBySlug(slug);
	}

	@Override
	@Transactional
	public Brand create(Brand brand) {
		// TODO Auto-generated method stub
		if (repo.existsBySlug(brand.getSlug())) {
			return null;
		}
		brand.setCreatedAt(new Date());
		brand.setModifiedAt(null);
		brand.setDeletedAt(null);
		brand.setActived(Boolean.TRUE);
		return repo.save(brand);
	}

	@Override
	@Transactional
	public Brand update(Long id, Brand brand) {
		// TODO Auto-generated method stub
		Brand updateBrand = repo.getById(id);
		updateBrand.setName(brand.getName());
		updateBrand.setDescription(brand.getDescription());
		if (brand.getSlug() != null && repo.getById(id).getSlug() != brand.getSlug()) {
			if (repo.existsBySlug(brand.getSlug())) {
				return null;
			}
		}
		updateBrand.setSlug(brand.getSlug());
		updateBrand.setCreatedAt(brand.getCreatedAt());
		updateBrand.setModifiedAt(brand.getModifiedAt());
		updateBrand.setDeletedAt(null);
		updateBrand.setActived(brand.getActived());
		return repo.saveAndFlush(updateBrand);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Brand deleteBrand = repo.getById(id);
		deleteBrand.setDeletedAt(new Date());
		deleteBrand.setActived(Boolean.FALSE);
		repo.saveAndFlush(deleteBrand);
	}

}
