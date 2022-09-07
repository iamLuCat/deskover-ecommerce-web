package com.deskover.service;

import java.util.List;

import com.deskover.entity.Notification;

public interface NotificationService {
	void getNotify(Long notifyId);
	void sendNotify(Notification notify);
	List<Notification> getAllNotifyOfUserId();
	List<Notification> getAllNotifyByOrderCode(String orderCode);
}