package com.deskover.dto.GHTKDto.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.deskover.dto.GHTKDto.entity.AddressGhtk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseData implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7550120044273553822L;
	
	private ArrayList<AddressGhtk> data;
}
