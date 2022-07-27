package com.deskover.dto.ghtk.entity;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsGhtk {
	
	@Id
	String name;
	
    Double weight;
    
    Integer quantity;
    
    String product_code;
   
}
