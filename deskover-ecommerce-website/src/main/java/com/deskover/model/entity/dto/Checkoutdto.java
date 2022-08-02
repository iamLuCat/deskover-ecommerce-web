package com.deskover.model.entity.dto;

import java.util.List;

import com.deskover.model.entity.database.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Checkoutdto {
    private List<Product> products;
    public void addProduct(Product product) {
        this.products.add(product);
    }
    public Checkoutdto(List<Product> products) {
		super();
		this.products = products;
	}
}
