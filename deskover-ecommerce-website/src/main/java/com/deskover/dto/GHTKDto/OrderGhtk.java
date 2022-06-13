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
public class OrderGhtk implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3271759845938382209L;
	
	private String id;
	
	private String pick_name;
	
	private String pick_address;
	
	private String pick_province;
	
	private String pick_district;
	
	private String pick_ward;
	
	private String pick_tel;
	
	private String tel;
	
	private String name;
	
	private String address;
	
	private String province;
	
	private String district;
	
	private String ward;
	
	private String hamlet;
	
	private String is_freeship;
	
	private String pick_date;
	
	private Integer pick_money;
	
	private String note;
	
	private Integer value;
	
	private String transport;
	
	private String pick_option;
	
	private String deliver_option;
	
	private Integer pick_session;
	
	private ArrayList<Integer> tags;
}
