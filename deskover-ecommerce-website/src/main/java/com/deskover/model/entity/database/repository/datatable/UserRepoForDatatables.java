package com.deskover.model.entity.database.repository.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.model.entity.database.Users;

public interface UserRepoForDatatables extends DataTablesRepository<Users, Long>{
	

}
