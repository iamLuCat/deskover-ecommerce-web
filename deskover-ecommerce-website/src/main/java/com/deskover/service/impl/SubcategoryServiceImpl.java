package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.dto.SubcategoryDto;
import com.deskover.entity.Product;
import com.deskover.entity.Subcategory;
import com.deskover.repository.SubcategoryRepository;
import com.deskover.repository.datatables.SubCategoryRepoForDatatables;
import com.deskover.service.CategoryService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;
import com.deskover.util.MapperUtil;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    SubcategoryRepository repo;

    @Autowired
    SubCategoryRepoForDatatables repoForDatatables;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    

    @Override
    public List<Subcategory> getByCategory(Long categoryId) {
        return repo.findByCategoryId(categoryId);
    }

    public List<Subcategory> getByActive(Boolean isActive) {
        return repo.findByActived(isActive);
    }

    public Subcategory getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public DataTablesOutput<Subcategory> getAllForDatatables(DataTablesInput input) {
        DataTablesOutput<Subcategory> subcategories = repoForDatatables.findAll(input);
        if (subcategories.getError() != null) {
            throw new IllegalArgumentException(subcategories.getError());
        }

        return subcategories;
    }

    @Override
    public DataTablesOutput<Subcategory> getByActiveForDatatables(DataTablesInput input, Boolean isActive) {
        DataTablesOutput<Subcategory> subcategories = repoForDatatables.findAll(input,
                (root, query, cb) -> cb.equal(root.get("actived"), isActive));
        if (subcategories.getError() != null) {
            throw new IllegalArgumentException(subcategories.getError());
        }
        return subcategories;
    }


    @Override
    public Subcategory create(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = MapperUtil.map(subcategoryDto, Subcategory.class);
        if (this.existsBySlug(subcategory)) {
            Subcategory subcategoryExists = repo.findBySlug(subcategory.getSlug());
            if (subcategoryExists != null && !subcategoryExists.getActived()) {
                subcategoryExists.setActived(true);
                subcategoryExists.setName(subcategory.getName());
                subcategoryExists.setDescription(subcategory.getDescription());
                subcategoryExists.setCategory(categoryService.getById(subcategoryDto.getCategoryId()));
                subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                return this.update(subcategoryExists);
            }
            throw new IllegalArgumentException("Slug đã tồn tại");
        } else {
            subcategory.setActived(true);
            subcategory.setCategory(categoryService.getById(subcategoryDto.getCategoryId()));
            subcategory.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            return repo.save(subcategory);
        }
    }

    @Override
    @Transactional
    public Subcategory update(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = MapperUtil.map(subcategoryDto, Subcategory.class);
        if (this.existsBySlug(subcategory)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }
        subcategory.setCategory(categoryService.getById(subcategoryDto.getCategoryId()));
        subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(subcategory);
    }

    @Override
    @Transactional
    public Subcategory update(Subcategory subcategory) {
        if (this.existsBySlug(subcategory)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }
        subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(subcategory);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Subcategory subcategory = this.getById(id);
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory not found");
        }

        subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        subcategory.setActived(Boolean.FALSE);
        subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Subcategory result = repo.save(subcategory);
        if (result.getActived() == Boolean.TRUE) {
            throw new IllegalArgumentException("Subcategory not deleted");
        }
    }

    @Override
    public void deleteMultiple(List<Subcategory> subcategories) {
        subcategories.forEach(subcategory -> {
            subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            subcategory.setActived(Boolean.FALSE);
            subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        	List<Product> products = productService.findBySubcategoryId(subcategory.getId());
        	productService.changeDelete(products, Boolean.FALSE);
        });
        repo.saveAll(subcategories);
    }

    @Override
    public Boolean existsBySlug(String slug) {
        return repo.existsBySlug(slug);
    }

    @Override
    public Boolean existsBySlug(Subcategory subcategory) {
        Subcategory subcategoryExists = repo.findBySlug(subcategory.getSlug());
        return (subcategoryExists != null && !subcategoryExists.getId().equals(subcategory.getId())) || productService.existsBySlug(subcategory.getSlug())
                || categoryService.existsBySlug(subcategory.getSlug());
    }

    @Override
    @Transactional
    public void deleteAll(List<Subcategory> subcategories) {
        repo.deleteAll(subcategories);
    }

    @Override
    public Subcategory changeActive(Long id) {
        Subcategory subcategory = this.getById(id);
        if (subcategory == null) {
            throw new IllegalArgumentException("Không tìm thấy danh mục");
        }
        if (subcategory.getCategory().getActived()) {
            if (subcategory.getActived()) {
                subcategory.setActived(Boolean.FALSE);
                subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
                subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                List<Product> products = productService.findBySubcategoryId(subcategory.getId());
                productService.changeDelete(products, Boolean.FALSE);
            } else {
                subcategory.setActived(Boolean.TRUE);
                subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
                subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                List<Product> products = productService.findBySubcategoryId(subcategory.getId());
                productService.changeDelete(products, Boolean.TRUE);
                repo.save(subcategory);

            }
            repo.save(subcategory);
            return subcategory;
        } else {
            throw new IllegalArgumentException("Danh mục cha đã bị vô hiệu hóa");
        }
    }

//	@Override
//	public Subcategory create(Subcategory subcategory) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
