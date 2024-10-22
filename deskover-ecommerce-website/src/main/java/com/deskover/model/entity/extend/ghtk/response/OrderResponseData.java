package com.deskover.model.entity.extend.ghtk.response;

import java.io.Serializable;
import java.util.List;

import com.deskover.model.entity.extend.ghtk.OrderGhtk;
import com.deskover.model.entity.extend.ghtk.ProductsGhtk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseData implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ProductsGhtk> products;
	private OrderGhtk order;
	

}
