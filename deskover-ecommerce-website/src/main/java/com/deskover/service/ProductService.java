package com.deskover.service;

import java.util.List;

import com.deskover.entity.Product;

public interface ProductService{
	List<Product> findByActived(Boolean actived,Integer p, Integer page);
}