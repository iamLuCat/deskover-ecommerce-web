package com.deskover.model.entity.dto.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	    private Long id;
	    
	    private String orderCode;
	    
	    private String qrCode;
	    
	    private String fullName;
	    
	    private String email;
	    
	    private String address;

	    private String province;

	    private String district;

	    private String ward;

	    private String tel;
	    
	    private List<OrderItemDto> orderItem;

	    private String createdAt;
	    
	    private String note;
	    
	    private String shipping_note;
	    
		private String code;
		
		private String status;
		
		private String totalPrice;
		
	    private String modifiedBy;

}
