package com.deskover.dto.ghtk;

import java.io.Serializable;
import java.util.ArrayList;

import javax.validation.constraints.NotBlank;

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
	
	@NotBlank(message = "Không bỏ trống tên của hàng")
	private String pick_name;
	
	@NotBlank(message = "Không bỏ trống địa chỉ lấy hàng")
	private String pick_address;
	
	@NotBlank(message = "Không bỏ trống Tỉnh/TP lấy hàng")
	private String pick_province;
	
	@NotBlank(message = "Không bỏ trống Quận/Huyện lấy hàng")
	private String pick_district;
	
	@NotBlank(message = "Không bỏ trống Phường/Xã lấy hàng")
	private String pick_ward;
	
	@NotBlank(message = "Không bỏ trống số điện thoại lấy hàng")
	private String pick_tel;
	
	@NotBlank(message = "Không bỏ trống số điện thoại nhận hàng")
	private String tel;
	
	@NotBlank(message = "Không bỏ trống tên người nhận")
	private String name;
	
	@NotBlank(message = "Không bỏ trống địa chỉ nhận hàng")
	private String address;
	
	@NotBlank(message = "Không bỏ trống Tỉnh/TP nhận hàng")
	private String province;
	
	@NotBlank(message = "Không bỏ trống Quận/Huyện nhận hàng")
	private String district;
	
	@NotBlank(message = "Không bỏ trống Phường/Xã nhận hàng")
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
