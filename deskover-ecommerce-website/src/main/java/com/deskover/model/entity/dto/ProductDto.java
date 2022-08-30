package com.deskover.model.entity.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.deskover.model.entity.database.Product;

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
    private Long quantity;
    private Timestamp modifiedAt;
    private String modifiedBy;
    private Boolean actived;


}
