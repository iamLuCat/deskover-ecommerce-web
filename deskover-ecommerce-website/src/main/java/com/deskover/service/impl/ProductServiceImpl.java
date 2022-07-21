package com.deskover.service.impl;

import com.deskover.constant.PathConstant;
import com.deskover.entity.Product;
import com.deskover.entity.ProductThumbnail;
import com.deskover.repository.ProductRepository;
import com.deskover.repository.ProductThumbnailRepository;
import com.deskover.repository.datatables.ProductRepoForDatatables;
import com.deskover.service.*;
import com.deskover.util.FileUtil;
import com.deskover.util.UrlUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ProductThumbnailRepository thumbnailRepository;

    @Autowired
    private ProductRepoForDatatables repoForDatatables;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private DiscountService discountService;

    public Page<Product> getByActive(Boolean isActive, Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        return repo.findByActived(isActive, pageable);
    }

    @Override
    public Page<Product> getByName(String name, Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));

        Page<Product> pages = repo.findByNameContaining(name, pageable);
        if (!pages.isEmpty()) {
            return pages;
        }
        Page<Product> pageSub = repo.findBySubCategoryNameContaining(name, pageable);
        if (!pageSub.isEmpty()) {
            return pageSub;
        }
        Page<Product> pageCate = repo.findBySubCategoryCategoryNameContaining(name, pageable);
        if (!pageCate.isEmpty()) {
            return pageCate;
        }
        throw new IllegalArgumentException("Không tìm thấy sản phẩm");

    }

    @Override
    @Transactional
    public Product create(Product product) {
        if (this.existsBySlug(product)) {
            Product productExists = repo.findBySlug(product.getSlug());
            if (productExists != null && !productExists.getActived()) {
                product.setId(productExists.getId());
            } else {
                throw new IllegalArgumentException("Slug đã tồn tại");
            }
        }
        product.setActived(Boolean.TRUE);
        return this.save(product);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (this.existsByOtherSlug(product)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }

        product.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        product.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        String sourcePath = PathConstant.TEMP_STATIC + product.getImg();
        if (FileUtils.getFile(sourcePath).exists()) {
            String destPath = PathConstant.PRODUCT_IMAGE_STATIC + product.getSlug();
            File imageFile = FileUtil.copyFile(sourcePath, destPath);
            product.setImg(imageFile.getName());
            product.setImgUrl(UrlUtil.getImageUrl(imageFile.getName(), PathConstant.PRODUCT_IMAGE));
        }
        Product savedProduct = repo.save(product);

        if (product.getProductThumbnails() != null) {
            int index = 0;
            for (ProductThumbnail thumbnail : product.getProductThumbnails()) {
                saveThumbnail(thumbnail, savedProduct, index);
                index++;
            }
        }

        FileUtil.removeFolder(PathConstant.TEMP_STATIC);
        return savedProduct;
    }

    ProductThumbnail saveThumbnail(ProductThumbnail productThumbnail, Product product, Integer index) {
        productThumbnail.setProduct(product);
        productThumbnail.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        productThumbnail.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        if (productThumbnail.getThumbnail() != null &&!productThumbnail.getThumbnail().isBlank()) {
            String sourcePath = PathConstant.TEMP_STATIC + productThumbnail.getThumbnail();
            if (FileUtils.getFile(sourcePath).exists()) {
                String destPath = PathConstant.PRODUCT_IMAGE_STATIC + product.getSlug() + "-" + index;
                File thumbnailFile = FileUtil.copyFile(sourcePath, destPath);
                productThumbnail.setThumbnail(thumbnailFile.getName());
                productThumbnail.setThumbnailUrl(UrlUtil.getImageUrl(thumbnailFile.getName(), PathConstant.PRODUCT_IMAGE));
            }
        }

        return thumbnailRepository.save(productThumbnail);
    }

    @Override
    @Transactional
    public Product changeActive(Long id) {
        Product product = this.getById(id);
        if (product == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        if (product.getSubCategory().getActived()) {
            product.setActived(!product.getActived());
            product.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            product.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            return repo.saveAndFlush(product);
        } else {
            throw new IllegalArgumentException("Danh mục đã bị vô hiệu hoá");
        }
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> optional = repo.findById(id);
        return optional.orElse(null);
    }

    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Boolean existsBySlug(String slug) {
        Product product = repo.findBySlug(slug);
        return product != null;
    }

    @Override
    public Product findBySlug(String slug) {
        return repo.findBySlug(slug);
    }

    @Override
    public Boolean existsByOtherSlug(Product product) {
        Product productExits = repo.findBySlug(product.getSlug());
        return (productExits != null && !productExits.getId().equals(product.getId()))
                || subcategoryService.existsBySlug(product.getSlug())
                || categoryService.existsBySlug(product.getSlug());
    }

    @Override
    public Boolean existsBySlug(Product product) {
        return existsBySlug(product.getSlug())
                || subcategoryService.existsBySlug(product.getSlug())
                || categoryService.existsBySlug(product.getSlug());
    }



    @Override
    public DataTablesOutput<Product> getByActiveForDatatables(
            @Valid DataTablesInput input,
            Boolean isActive,
            Long categoryId,
            Long brandId,
            Boolean isDiscount) {
        DataTablesOutput<Product> products = null;
        products = repoForDatatables.findAll(input, (Specification<Product>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (isActive != null) {
                predicates.add(cb.equal(root.get("actived"), isActive));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("subCategory").get("category").get("id"), categoryId));
            }
            if (brandId != null) {
                predicates.add(cb.equal(root.get("brand").get("id"), brandId));
            }
            if (isDiscount != null) {
                if (isDiscount) {
                    predicates.add(root.get("discount").isNotNull());
                } else {
                    predicates.add(root.get("discount").isNull());
                }
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        if (products.getError() != null) {
            throw new IllegalArgumentException(products.getError());
        }
        return products;
    }

    public DataTablesOutput<Product> getByActiveForDatatables(
            @Valid DataTablesInput input,
            Boolean isActive,
            Boolean isExistsByDiscount,
            Long categoryId) {
        DataTablesOutput<Product> products = null;
        products = repoForDatatables.findAll(input, (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (isActive != null) {
                predicate.getExpressions().add(cb.equal(root.get("actived"), isActive));
            }
            if (isExistsByDiscount != null) {
                if (isExistsByDiscount) {
                    predicate.getExpressions().add(cb.isNotNull(root.get("discount")));
                } else {
                    predicate.getExpressions().add(cb.isNull(root.get("discount")));
                }
            }
            if (categoryId != null) {
                predicate.getExpressions().add(cb.equal(root.get("subCategory").get("category").get("id"), categoryId));
            }
            return predicate;
        });
        if (products.getError() != null) {
            throw new IllegalArgumentException(products.getError());
        }
        return products;
    }

    @Override
    public void changeDelete(List<Product> products, Boolean isActive) {

        products.forEach(product -> {
            product.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            product.setActived(isActive);
            product.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        });
        repo.saveAll(products);

    }

    @Override
    public List<Product> getBySubcategoryId(Long id) {

        return repo.findBySubCategoryId(id);
    }

    @Override
    public void changeActiveSubcategoty(Long id) {
        Product product = this.getById(id);
        if (product == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        subcategoryService.changeActive(product.getSubCategory().getId());

    }

	@Override
	public List<Product> getProductByCreateAtDesc(Boolean active) {
		
		return repo.findByActivedOrderByModifiedAtDesc(active);
		
	}

}