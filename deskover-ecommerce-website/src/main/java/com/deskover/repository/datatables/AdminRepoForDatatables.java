package com.deskover.repository.datatables;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Administrator;

public interface AdminRepoForDatatables extends DataTablesRepository<Administrator, Long>{

}
