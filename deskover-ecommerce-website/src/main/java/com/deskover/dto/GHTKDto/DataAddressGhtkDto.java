package com.deskover.dto.GHTKDto;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataAddressGhtkDto implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7550120044273553822L;
	
	private ArrayList<AddressGhtkDto> data;
}
