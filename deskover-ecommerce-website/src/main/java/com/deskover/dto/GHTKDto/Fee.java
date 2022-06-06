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
public class Fee implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4956070063208974668L;
	
	private String pick_province;
	
	private String pick_district;
	
	private String province;
	
	private String district;
	
	private String address;
	
	private Double weight;
	
	private Integer value;
	
	private String transport;
	
	private String deliver_option;
	
	private ArrayList<Integer> tags;

}
