package com.deskover.service;

import com.deskover.model.entity.dto.ecommerce.FormReview;

public interface RatingService {
	public void postReview(FormReview formReview);

	Long totalRatings();
}
