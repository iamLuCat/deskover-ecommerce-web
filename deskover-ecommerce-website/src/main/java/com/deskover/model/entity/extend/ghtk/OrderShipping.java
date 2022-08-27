package com.deskover.model.entity.extend.ghtk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderShipping {
	String partner_id; // orderID
	String label; // Mã vận đơn
	Double fee; // phí ship
	String estimated_pick_time;//dự kiến lấy hàng
	String estimated_deliver_time;//dự kiến giao hàng
	String status;
	String status_text;
	

}
