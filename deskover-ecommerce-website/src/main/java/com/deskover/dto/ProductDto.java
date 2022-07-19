package com.deskover.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.deskover.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable{

	private static final long serialVersionUID = 8108059535055901960L;
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Double price;
    private String image;
    private Timestamp modifiedAt;
    private String modifiedBy;
    private Boolean actived;
    private SubcategoryDto subcategory;
    private BrandDto brand;
    
    public ProductDto(Product product) {
    	this.id = product.getId();
    	this.name = product.getName();
    	this.slug = product.getSlug();
    	this.description = product.getDescription();
    	this.price = product.getPrice();
    	this.image = product.getImage();
    	this.modifiedAt = product.getModifiedAt();
    	this.modifiedBy = product.getModifiedBy();
    	this.actived = product.getActived();
    	this.subcategory = new SubcategoryDto(product.getSubCategory());
    	this.brand = new BrandDto(product.getBrand());
    }
}
