package com.deskover.service.impl;

import com.deskover.model.entity.database.Rating;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.database.repository.RatingRepository;
import com.deskover.model.entity.dto.ecommerce.FormReview;
import com.deskover.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class RatingServiceImpl implements RatingService {
	
	@Autowired
	private RatingRepository ratingRepo;

	@Autowired
	private ProductRepository productRepo;

	@Override
	public void postReview(FormReview formReview) {
		
		Rating rating = new Rating();
		rating.setProduct(productRepo.findBySlug(formReview.getProduct()));
		rating.setPoint(formReview.getPoint());
		rating.setFullname(formReview.getName());
		rating.setContent(formReview.getContent());
		rating.setActived(true);
		rating.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		
		ratingRepo.save(rating);
	}

	@Override
	public Long totalRatings() {
		return ratingRepo.count();
	}

}
