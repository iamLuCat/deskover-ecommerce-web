package com.deskover.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
	
	private String name;
	
	private Integer quantity;
	
	private String price;

}
