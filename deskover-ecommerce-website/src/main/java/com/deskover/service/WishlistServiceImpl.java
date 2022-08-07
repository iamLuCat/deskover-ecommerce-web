package com.deskover.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.deskover.model.entity.database.Wishlist;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.database.repository.WishlistRepository;
import com.deskover.model.entity.dto.ecommerce.WishlistDTO;

@Component
public class WishlistServiceImpl implements WishlistService {
	
	@Autowired
	WishlistRepository repo;
	
	@Autowired
	ProductRepository pRepo;
	
	@Autowired
	UserRepository uRepo;

	@Override
	public List<String> setWishlist(String slug, String name) {
		
		Wishlist wishlist = repo.getWishlist(name, slug);
		if(Objects.isNull(wishlist)) {
			wishlist = new Wishlist();
			wishlist.setActived(true);
			wishlist.setProduct(pRepo.findBySlug(slug));
			wishlist.setUser(uRepo.findByUsername(name));
		}else {
			wishlist.setActived(!wishlist.getActived());
		}
		wishlist.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		repo.save(wishlist);
		return repo.findWishlist(name).stream().map(w -> w.getProduct().getSlug()).collect(Collectors.toList());
	}

	@Override
	public List<String> getWishlist(String name) {
		return repo.findWishlist(name).stream().map(w -> w.getProduct().getSlug()).collect(Collectors.toList());
	}

	@Override
	public WishlistDTO getWishlist(String name, Integer p) {
		Page<Wishlist> pWishlist = repo.findWishlist(name, PageRequest.of(p, 4));
		if(pWishlist.getTotalPages() - 1 < p) {
			p = pWishlist.getTotalPages() - 1;
			pWishlist = repo.findWishlist(name, PageRequest.of(p, 4));
		}
		
		return new WishlistDTO(pWishlist, p);
	}

}
