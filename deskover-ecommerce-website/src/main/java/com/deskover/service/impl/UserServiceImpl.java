package com.deskover.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deskover.entity.User;
import com.deskover.repository.UserRepository;
import com.deskover.repository.datatables.UserRepoForDatatables;
import com.deskover.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	UserRepoForDatatables repoForDatatables;

	@Override
	@Transactional
	public void changeActived(Long id) {
		User user = repo.findById(id).orElse(null);
		if(user == null) {
			throw new IllegalArgumentException("Không tìm thấy user");
		}
		user.setActived(!user.getActived());
		user.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		user.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		repo.saveAndFlush(user);
	}

	@Override
	public DataTablesOutput<User> getByActiveForDatatables(DataTablesInput input, Boolean isActive) {
		DataTablesOutput<User> Users = repoForDatatables.findAll(input,
				(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
		if (Users.getError() != null) {
			throw new IllegalArgumentException(Users.getError());
		}
		return Users;
	}

	@Override
	public User findById(Long id) {
		return repo.getById(id);
	}

	@Override
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}

}
