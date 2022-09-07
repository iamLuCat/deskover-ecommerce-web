package com.deskover.dto.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartLocal {
	private Integer quantity;
	private String slug;
}
