package com.deskover.dto.GHTKDto.reponse;

import java.io.Serializable;
import java.util.ArrayList;

import com.deskover.dto.GHTKDto.entity.OrderGhtk;
import com.deskover.dto.GHTKDto.entity.ProductsGhtk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReponseData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private ArrayList<ProductsGhtk> products;
	private OrderGhtk order;
	

}
