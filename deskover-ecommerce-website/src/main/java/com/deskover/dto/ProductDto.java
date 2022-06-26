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
public class ProductDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 8108059535055901960L;
	
    private Long id;

    private String name;
    
    private String slug;

    private String description;

    private Double price;

    private String image;

    private Timestamp createdDate;

    private Timestamp modifiedDate;
    
    private String modifiedUser;

    private Boolean actived;
    
    private Long subcategogyId;
    
    private Long brandId;
    
    private Long discountId;
    
    public ProductDto(Product product ) {
    	this.id = product.getId();
    	this.name = product.getName();
    	this.slug = product.getSlug();
    	this.description = product.getDescription();
    	this.price = product.getPrice();
    	this.image = product.getImage();
    	this.createdDate = product.getCreatedDate();
    	this.modifiedDate = product.getModifiedDate();
    	this.modifiedUser = product.getModifiedUser();
    	this.actived = product.getActived();
    	this.subcategogyId = product.getSubCategory().getId();
    	this.brandId = product.getBrand().getId();
    	this.discountId = product.getDiscount().getId();
    	
    }

	
}
