package com.deskover.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalByCategory implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -5396567617867475300L;
	
	private String name;
	private Double totalProduct;
	
}
