package com.deskover.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deskover.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Page<Product> findByActived(Boolean actived, Pageable Page);
	
	//Like Name
	Page<Product> findByNameContaining(String name, Pageable Page);
	
	//Like SubCategory
	Page<Product> findBySubCategoryNameContaining(String name, Pageable Page);
	
	//Like Category
	Page<Product> findBySubCategoryCategoryNameContaining(String name, Pageable Page);
	
	Boolean existsBySlug(String slug);
	
	Product findBySlug(String slug);
	
	List<Product> findBySubCategoryId(Long id);

	@Query(value = "SELECT p FROM Product p "
			+ "WHERE p.name LIKE %?1% "
			+ "AND p.subCategory.category.name LIKE %?2% "
			+ "AND p.subCategory.name LIKE %?3% "
			+ "AND p.price BETWEEN ?4 AND ?5 "
			+ "AND (coalesce(?6) is null OR p.brand.name IN ?6) ",
			nativeQuery = false)
	List<Product> search(
			String keyword, 
			String categories, 
			String subcategories, 
			Double minPrice,
			Double maxPrice,
			List<String> brands);
	
	@Query(value = "SELECT p FROM Product p "
			+ "WHERE p.name LIKE %?1% "
			+ "AND p.subCategory.category.slug LIKE %?2% "
			+ "AND p.subCategory.slug LIKE %?3% "
			+ "AND p.price BETWEEN ?4 AND ?5 "
			+ "AND (coalesce(?6) is null OR p.brand.name IN ?6) ",
			nativeQuery = false)
	Page<Product> searchPage(
			String keyword, 
			String categories, 
			String subcategories, 
			Double minPrice,
			Double maxPrice,
			List<String> brands,
			Pageable pageable);
	//app custumer
	Page<Product> findByActivedAndQuantityGreaterThanOrderByModifiedAtDesc(Boolean active,Long quantity,Pageable Page);
	
	Page<Product> findByActivedAndSubCategoryCategoryId(Boolean active,Long categoryId, Pageable Page);
	
	Page<Product> findByActivedAndSubCategoryId(Boolean active,Long categoryId, Pageable Page);
	
	Page<Product> findByFlashSaleActivedAndDiscountActived(Boolean activeFlashSale, Boolean activeDiscount,Pageable Page);
	
	
}