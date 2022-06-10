package com.deskover.dto.GHTKDto.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.deskover.dto.GHTKDto.OrderGhtk;
import com.deskover.dto.GHTKDto.ProductsGhtk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private ArrayList<ProductsGhtk> products;
	private OrderGhtk order;
	

}
