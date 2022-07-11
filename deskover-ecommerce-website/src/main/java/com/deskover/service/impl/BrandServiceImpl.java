package com.deskover.service.impl;

import com.deskover.entity.Brand;
import com.deskover.entity.Category;
import com.deskover.repository.BrandRepository;
import com.deskover.repository.datatables.BrandRepoForDatatables;
import com.deskover.service.BrandService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository repo;

    @Autowired
    BrandRepoForDatatables repoForDatatables;

    @Autowired
    ProductService productService;

    @Autowired
    SubcategoryService subcategoryService;

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
    public Boolean existsBySlug(Brand brand) {
        Brand brandExists = repo.findBySlug(brand.getSlug());
        return (brandExists != null && !brandExists.getId().equals(brand.getId()))
                || productService.existsBySlug(brand.getSlug())
                || subcategoryService.existsBySlug(brand.getSlug());
    }


    @Override
    @Transactional
    public Brand create(Brand brand) {
        if (this.existsBySlug(brand)) {
            Brand brandExists = this.getBySlug(brand.getSlug());
            if (brandExists.getActived() == Boolean.FALSE) {
                brand.setId(brandExists.getId());
            } else {
                throw new IllegalArgumentException("Slug đã tồn tại");
            }
        }
        brand.setActived(Boolean.TRUE);
        brand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        brand.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(brand);
    }

    @Override
    @Transactional
    public Brand update(Brand brand) {
        if(this.existsBySlug(brand)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }
        brand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        brand.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(brand);
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
        deleteBrand.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        repo.save(deleteBrand);
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
            currentBrand.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.save(currentBrand);
        }else{
            currentBrand.setActived(Boolean.TRUE);
            currentBrand.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            currentBrand.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.save(currentBrand);
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

	@Override
	public List<Brand> getByActived(Boolean isActive) {
		return repo.findByActived(isActive);
	}

}
