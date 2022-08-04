package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.Notification;
import com.deskover.model.entity.database.repository.NotificationRepository;
import com.deskover.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationRepository repository; 

	@Override
	public void getNotify(Long id) {
		Notification notifyRequest = repository.findById(id).orElse(null);
		if(notifyRequest == null) {
			throw new IllegalArgumentException("Thông báo này không tồn tại");
		}
		if(notifyRequest.getIsWatched() == false) {
			notifyRequest.setIsWatched(Boolean.TRUE);
			repository.saveAndFlush(notifyRequest);
		}
	}

	@Override
	public void sendNotify(Notification notify) {
		repository.save(notify);
	}

	@Override
	public List<Notification> getAllNotifyOfUserId() {
		return repository.findByUserUsernameOrderByCreatedAtDesc(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
