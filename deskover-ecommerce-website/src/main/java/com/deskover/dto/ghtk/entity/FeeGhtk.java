package com.deskover.dto.ghtk.entity;

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
public class FeeGhtk implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4956070063208974668L;
	
	//String - Tên tỉnh/thành phố nơi lấy hàng hóa
	private String pick_province;
	
	//String - Tên quận/huyện nơi lấy hàng hóa
	private String pick_district;
	
	//String - Tên tỉnh/thành phố của người nhận hàng hóa
	private String province;
	
	//String - Tên quận/huyện của người nhận hàng hóa
	private String district;
	
	//String - Địa chỉ chi tiết của người nhận hàng, ví dụ: Chung cư CT1, ngõ 58, đường Trần Bình
	private String address;
	
	//Cân nặng của gói hàng, đơn vị sử dụng Gram
	private Double weight;
	
	//Giá trị đơn hàng
	private Integer value;
	
	// Phương thức vận chuyển : road ( bộ ) hoặc fly hoặc null
	private String transport;
	
	//Nhận 1 trong 2 giá trị xteam/none; xteam là giao nhanh. none là vận chuyển bình thường
	private String deliver_option;
	
	private ArrayList<Integer> tags;

}
