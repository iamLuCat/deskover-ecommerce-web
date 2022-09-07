package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	List<Notification> findByUserUsernameOrderByCreatedAtDesc(String username);
	List<Notification> findByUserUsernameAndOrderCodeOrderByCreatedAtAsc(String username,String orderCode);
}
