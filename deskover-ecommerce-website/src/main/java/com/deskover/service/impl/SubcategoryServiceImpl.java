package com.deskover.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.Subcategory;
import com.deskover.model.entity.database.repository.SubcategoryRepository;
import com.deskover.model.entity.database.repository.datatable.SubCategoryRepoForDatatables;
import com.deskover.other.constant.PathConstant;
import com.deskover.other.util.FileUtil;
import com.deskover.other.util.UrlUtil;
import com.deskover.service.CategoryService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;

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

    @Override
    public List<Subcategory> getAll(Boolean isActive, Long categoryId) {
        if (categoryId == null) {
            return repo.findByActived(isActive);
        } else {
            return repo.findByActivedAndCategoryId(isActive, categoryId);
        }
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
    public DataTablesOutput<Subcategory> getByActiveForDatatables(DataTablesInput input, Boolean isActive, Long categoryId) {
        DataTablesOutput<Subcategory> subcategories = null;
        if (categoryId == null) {
            subcategories = repoForDatatables.findAll(input,
                    (root, query, cb) -> cb.equal(root.get("actived"), isActive));
        } else {
            subcategories = repoForDatatables.findAll(input,
                    (root, query, cb) ->
                            cb.and(cb.equal(root.get("actived"), isActive),
                            cb.equal(root.get("category").get("id"), categoryId)));
        }
        if (subcategories.getError() != null) {
            throw new IllegalArgumentException(subcategories.getError());
        }
        return subcategories;
    }

    @Override
    public Subcategory create(Subcategory subcategory) {
        if (this.existsBySlug(subcategory)) {
            Subcategory subcategoryExists = repo.findBySlug(subcategory.getSlug());
            if (subcategoryExists != null && !subcategoryExists.getActived()) {
                subcategory.setId(subcategoryExists.getId());
            } else {
                throw new IllegalArgumentException("Slug đã tồn tại");
            }
        }
        subcategory.setActived(true);
        subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        return repo.save(subcategory);
    }

    @Override
    @Transactional
    public Subcategory update(Subcategory subcategory) {
        if (this.existsByOtherSlug(subcategory)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }
        subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        String sourcePath = PathConstant.TEMP_STATIC + subcategory.getImg();
        if (FileUtils.getFile(sourcePath).exists()) {
            String destPath = PathConstant.SUBCATEGORY_IMAGE_STATIC + subcategory.getSlug();
            File imageFile = FileUtil.copyFile(sourcePath, destPath);
            subcategory.setImg(imageFile.getName());
            subcategory.setImgUrl(UrlUtil.getImageUrl(imageFile.getName(), PathConstant.SUBCATEGORY_IMAGE));
        }

        FileUtil.removeFolder(PathConstant.TEMP_STATIC);
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
        	List<Product> products = productService.getBySubcategoryId(subcategory.getId());
        	productService.changeDelete(products, Boolean.FALSE);
        });
        repo.saveAll(subcategories);
    }

    @Override
    public Boolean existsBySlug(String slug) {
        return repo.existsBySlug(slug);
    }

    @Override
    public Boolean existsByOtherSlug(Subcategory subcategory) {
        Subcategory subcategoryExists = repo.findBySlug(subcategory.getSlug());
        return (subcategoryExists != null && !subcategoryExists.getId().equals(subcategory.getId())) || productService.existsBySlug(subcategory.getSlug())
                || categoryService.existsBySlug(subcategory.getSlug());
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
                List<Product> products = productService.getBySubcategoryId(subcategory.getId());
                productService.changeDelete(products, Boolean.FALSE);
            } else {
                subcategory.setActived(Boolean.TRUE);
                subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
                subcategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                List<Product> products = productService.getBySubcategoryId(subcategory.getId());
                productService.changeDelete(products, Boolean.TRUE);
                repo.save(subcategory);

            }
            repo.save(subcategory);
            return subcategory;
        } else {
            throw new IllegalArgumentException("Danh mục cha đã bị vô hiệu hóa");
        }
    }

}
