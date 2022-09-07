package com.deskover.service.impl;

import com.deskover.dto.ecommerce.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartDTO {
	
	private Item item;
	private Integer quantity;
}
