package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import com.deskover.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deskover.entity.Brand;
import com.deskover.repository.BrandRepository;
import com.deskover.repository.datatables.BrandRepoForDatatables;
import com.deskover.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository repo;

    @Autowired
    BrandRepoForDatatables repoForDatatables;

    @Override
    public List<Brand> getAll() {
        return repo.findAll();
    }

    @Override
    public List<Brand> getAllBrandIsActived() {
        return repo.findByActived(Boolean.TRUE);
    }

    @Override
    public Brand getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Brand getBySlug(String slug) {
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
            throw new IllegalArgumentException("Slug này đã tồn tại");
        }
        brand.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        brand.setModifiedAt(null);
        brand.setActived(Boolean.TRUE);
        brand.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(brand);
    }

    @Override
    @Transactional
    public Brand update(Brand brand) {
        if (brand.getSlug() != null) {
            if(repo.getById(brand.getId()).getSlug().equals(brand.getSlug())){
                brand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
                brand.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
                return repo.saveAndFlush(brand);
            }else{
                if(repo.existsBySlug(brand.getSlug())){
                    throw new IllegalArgumentException("Slug này đã tồn tại");
                }
            }
        }
        brand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        brand.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.saveAndFlush(brand);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Brand deleteBrand = repo.findById(id).orElse(null);
        if(deleteBrand == null){
            throw new IllegalArgumentException("Brand này không tồn tại");
        }
        deleteBrand.setModifiedAt(new Timestamp((System.currentTimeMillis())));
        deleteBrand.setActived(Boolean.FALSE);
        deleteBrand.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
        repo.saveAndFlush(deleteBrand);
    }

    @Override
    public void changeActived(Long id) {
        Brand currentBrand = repo.findById(id).orElse(null);
        if(currentBrand == null){
            throw new IllegalArgumentException("Brand này không tồn tại");
        }
        if(currentBrand.getActived()){
            currentBrand.setActived(Boolean.FALSE);
            currentBrand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            currentBrand.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.saveAndFlush(currentBrand);
        }else{
            currentBrand.setActived(Boolean.TRUE);
            currentBrand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            currentBrand.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.saveAndFlush(currentBrand);
        }
    }

    @Override
    public DataTablesOutput<Brand> getByActiveForDatatables(DataTablesInput input, Boolean isActive) {
        DataTablesOutput<Brand> brands = repoForDatatables.findAll(input, (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("actived"), isActive));
        if (brands.getError() != null) {
            throw new IllegalArgumentException(brands.getError());
        }
        return brands;
    }

}
