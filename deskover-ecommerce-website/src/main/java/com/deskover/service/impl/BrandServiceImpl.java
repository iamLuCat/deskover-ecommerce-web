package com.deskover.service.impl;

import java.sql.Timestamp;
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
		return repo.findAll();
	}

	@Override
	public List<Brand> getAllBrandIsActived() {
		return repo.findByActived(Boolean.TRUE);
	}

	@Override
	public Brand get(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Brand get(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public Boolean existsBySlug(String slug) {
		return repo.existsBySlug(slug);
	}

	@Override
	@Transactional
	public Brand create(Brand brand) {
		if (repo.existsBySlug(brand.getSlug())) {
			return null;
		}
		brand.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		brand.setModifiedAt(null);
		brand.setDeletedAt(null);
		brand.setActived(Boolean.TRUE);
		return repo.save(brand);
	}

	@Override
	@Transactional
	public Brand update(Long id, Brand brand) {
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
		Brand deleteBrand = repo.getById(id);
		deleteBrand.setDeletedAt(new Timestamp((System.currentTimeMillis())));
		deleteBrand.setActived(Boolean.FALSE);
		repo.saveAndFlush(deleteBrand);
	}

}
