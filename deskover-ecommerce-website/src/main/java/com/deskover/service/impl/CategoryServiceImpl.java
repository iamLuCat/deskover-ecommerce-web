package com.deskover.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.model.entity.database.Category;
import com.deskover.model.entity.database.Subcategory;
import com.deskover.model.entity.database.repository.CategoryRepository;
import com.deskover.model.entity.database.repository.datatable.CategoryRepoForDatatables;
import com.deskover.other.constant.PathConstant;
import com.deskover.other.util.FileUtil;
import com.deskover.other.util.UrlUtil;
import com.deskover.service.CategoryService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repo;

    @Autowired
    CategoryRepoForDatatables repoForDatatables;

    @Autowired
    SubcategoryService subcategoryService;

    @Autowired
    private ProductService productService;

    @Override
    public Boolean existsBySlug(String slug) {
        Category category = repo.findBySlug(slug);
        return category != null;
    }

    // Check if the slug is already in use by another category
    @Override
    public Boolean existsByOtherSlug(Category category) {
        Category categoryExists = repo.findBySlug(category.getSlug());
        return (categoryExists != null && !categoryExists.getId().equals(category.getId()))
                || productService.existsBySlug(category.getSlug())
                || subcategoryService.existsBySlug(category.getSlug());
    }

    @Override
    public Boolean existsBySlug(Category category) {
        return existsBySlug(category.getSlug())
                || productService.existsBySlug(category.getSlug())
                || subcategoryService.existsBySlug(category.getSlug());
    }

    @Override
    public List<Category> getByActived(Boolean isActive) {
        return repo.findByActived(isActive);
    }

    @Override
    public DataTablesOutput<Category> getAllForDatatables(DataTablesInput input) {
        DataTablesOutput<Category> categories = repoForDatatables.findAll(input);
        if (categories.getError() != null) {
            throw new IllegalArgumentException(categories.getError());
        }
        return categories;
    }

    @Override
    public DataTablesOutput<Category> getByActiveForDatatables(DataTablesInput input, Boolean isActive) {
        DataTablesOutput<Category> categories = repoForDatatables.findAll(input, (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("actived"), isActive));
        if (categories.getError() != null) {
            throw new IllegalArgumentException(categories.getError());
        }
        return categories;
    }

    @Override
    public Page<Category> getByActived(Boolean isActive, Integer page, Integer size) {
        return repo.findByActived(isActive, PageRequest.of(page, size));
    }

    @Override
    public Category getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Category create(Category category) {
        if (this.existsBySlug(category)) {
            Category categoryExists = repo.findBySlug(category.getSlug());
            if (categoryExists != null && !categoryExists.getActived()) {
                category.setId(categoryExists.getId());
            } else {
                throw new IllegalArgumentException("Slug đã tồn tại");
            }
        }
        category.setActived(Boolean.TRUE);
        return update(category);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        if (this.existsByOtherSlug(category)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }

        category.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        category.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        String sourcePath = PathConstant.TEMP_STATIC + category.getImg();
        if (FileUtils.getFile(sourcePath).exists()) {
            String destPath = PathConstant.CATEGORY_IMAGE_STATIC + category.getSlug();
            File imageFile = FileUtil.copyFile(sourcePath, destPath);
            category.setImg(imageFile.getName());
        }
        FileUtil.removeFolder(PathConstant.TEMP_STATIC);

        return repo.save(category);
    }

    @Override
    @Transactional
    public void changeActived(Long id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        category.setActived(!category.getActived());
        category.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        category.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        repo.saveAndFlush(category);
        // Delete all subcategories of this category
        List<Subcategory> subcategories = subcategoryService.getByCategory(id);
        if (subcategories != null) {
            subcategoryService.deleteMultiple(subcategories);
        }

    }

}
