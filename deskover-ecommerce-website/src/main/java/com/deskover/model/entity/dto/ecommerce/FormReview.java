package com.deskover.model.entity.dto.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormReview {
	
	private String product;
	private String content;
	private Integer point;
	private String email;
	private String name;
}
