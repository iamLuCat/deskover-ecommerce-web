package com.deskover.dto.GHTKDto;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataGhtk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private ArrayList<Products> products;
	private Order order;
	

}
