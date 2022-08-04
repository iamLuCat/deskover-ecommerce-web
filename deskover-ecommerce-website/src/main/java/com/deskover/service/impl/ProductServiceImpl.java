package com.deskover.service.impl;

import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.repository.FlashSaleRepository;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.database.repository.ProductThumbnailRepository;
import com.deskover.model.entity.database.repository.datatable.ProductRepoForDatatables;
import com.deskover.other.constant.PathConstant;
import com.deskover.other.util.FileUtil;
import com.deskover.service.CategoryService;
import com.deskover.service.FlashSaleService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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
import java.util.concurrent.atomic.AtomicInteger;

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
    private FlashSaleService flashSaleService;
    
    @Autowired
    private FlashSaleRepository flashSaleRepository;


    public Page<Product> getByActive(Boolean isActive, Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        return repo.findByActived(isActive, pageable);
    }

    @Override
    public Page<Product> getByName(String name, Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<Product> pages = repo.findByNameContainingOrSubCategoryNameContainingOrSubCategoryCategoryNameContainingOrBrandNameContaining(name,name,name,name,pageable);
        if (!pages.isEmpty()) {
            return pages;
        }
        throw new IllegalArgumentException("Không tìm thấy sản phẩm");

    }
    
    @Override
    @Transactional
    public Product create(Product product, Boolean isCopy) {
        if (this.existsBySlug(product)) {
            Product productExists = repo.findBySlug(product.getSlug());
            if (productExists != null && !productExists.getActived()) {
                product.setId(productExists.getId());
            } else {
                throw new IllegalArgumentException("Slug đã tồn tại");
            }
        }

        product.setActived(Boolean.TRUE);
        return this.save(product, isCopy);
    }

    @Override
    @Transactional
    public Product save(Product product, Boolean isCopy) {
        if (this.existsByOtherSlug(product)) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }

        product.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        product.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        String sourcePath = (isCopy ? PathConstant.PRODUCT_IMAGE_STATIC : PathConstant.TEMP_STATIC) + product.getImg();
        if (FileUtils.getFile(sourcePath).exists()) {
            String destPath = PathConstant.PRODUCT_IMAGE_STATIC + product.getSlug();
            File imageFile = FileUtil.copyFile(sourcePath, destPath);
            product.setImg(imageFile.getName());
//            product.setImgUrl(UrlUtil.getImageUrl(imageFile.getName(), PathConstant.PRODUCT_IMAGE));
        }
        Product savedProduct = repo.save(product);

        AtomicInteger index = new AtomicInteger(1);
        product.getProductThumbnails().forEach(thumbnail -> {
            if (thumbnail != null && thumbnail.getThumbnail() != null && !thumbnail.getThumbnail().isBlank()) {
                if (isCopy) {thumbnail.setId(null);}
                thumbnail.setProduct(product);
                thumbnail.setModifiedAt(new Timestamp(System.currentTimeMillis()));
                thumbnail.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

                String sourcePathThumbnail = (isCopy ? PathConstant.PRODUCT_IMAGE_STATIC : PathConstant.TEMP_STATIC)
                        + thumbnail.getThumbnail();
                if (FileUtils.getFile(sourcePathThumbnail).exists()) {
                    String destPathThumbnail = PathConstant.PRODUCT_IMAGE_STATIC + product.getSlug()
                            + "-thumbnail-" + index;
                    File imageFileThumbnail = FileUtil.copyFile(sourcePathThumbnail, destPathThumbnail);
                    thumbnail.setThumbnail(imageFileThumbnail.getName());
//                    thumbnail.setThumbnailUrl(UrlUtil.getImageUrl(imageFileThumbnail.getName(), PathConstant.PRODUCT_IMAGE));
                }
                thumbnailRepository.save(thumbnail);
            }
            index.getAndIncrement();
        });


        FileUtil.removeFolder(PathConstant.TEMP_STATIC);
        return savedProduct;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.save(product, false);
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
    public Product findBySlug(String slug) {
        return repo.findBySlug(slug);
    }

    @Override
    public Boolean existsBySlug(String slug) {
        Product product = repo.findBySlug(slug);
        return product != null;
    }

    @Override
    public Boolean existsBySlug(Product product) {
        return existsBySlug(product.getSlug())
                || subcategoryService.existsBySlug(product.getSlug())
                || categoryService.existsBySlug(product.getSlug());
    }

    @Override
    public Boolean existsByOtherSlug(Product product) {
        Product productExits = repo.findBySlug(product.getSlug());
        return (productExits != null && !productExits.getId().equals(product.getId()))
                || subcategoryService.existsBySlug(product.getSlug())
                || categoryService.existsBySlug(product.getSlug());
    }


    @Override
    public DataTablesOutput<Product> getByActiveForDatatables(
            @Valid DataTablesInput input,
            Boolean isActive,
            Long categoryId,
            Long brandId,
            Boolean isDiscount,
            Boolean isFlashSale
    ) {
        DataTablesOutput<Product> products = repoForDatatables.findAll(input, (root, query, cb) -> {
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
            if (isFlashSale != null) {
                if (isFlashSale) {
                    predicates.add(root.get("flashSale").isNotNull());
                } else {
                    predicates.add(root.get("flashSale").isNull());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
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
    public Page<Product> getProductByCreateAtDesc(Boolean active, Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(8));
        Page<Product> products = repo.findByActivedAndAndDiscountAndQuantityGreaterThanOrderByModifiedAtDesc(active,null, (long) 0, pageable);
        if (products == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        return products;
    }

    @Override
    public Page<Product> getProductByCategoryId(Boolean active, Long categoryId, Optional<Integer> page,
                                                Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(8));
        Page<Product> products = repo.findByActivedAndSubCategoryCategoryIdAndDiscount(active, categoryId, null, pageable);
        if (products == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        return products;
    }

    @Override
    public Page<Product> getProductBySubId(Boolean active, Long subId, Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(8));
        Page<Product> products = repo.findByActivedAndSubCategoryId(active, subId, pageable);
        if (products == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        return products;
    }

    @Override
    public Page<Product> doGetProductSale(Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(8));
        Page<Product> products = repo.findByFlashSaleActivedAndDiscountActived(Boolean.TRUE, Boolean.TRUE, pageable);
        if (products.getContent().isEmpty()) {
            return null;
        }
        System.out.println(">>>>>>>>>>>>>>>>" + products.getContent().stream().findFirst().get().getFlashSale().getEndDate());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp timeFlashSale = products.getContent().stream().findFirst().get().getFlashSale().getEndDate();
        if (timeFlashSale.getTime() < timestamp.getTime()) {
        	FlashSale flashSale = flashSaleService.getById(products.getContent().stream().findFirst().get().getFlashSale().getId());
        	flashSale.setActived(Boolean.FALSE);
        	flashSaleRepository.saveAndFlush(flashSale);
            System.out.println("null>>>>>>>>>>>");
            return null;
        }
        return products;
    }



}