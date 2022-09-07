package com.deskover.reponsitory.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Users;

public interface UserRepoForDatatables extends DataTablesRepository<Users, Long>{
	

}
