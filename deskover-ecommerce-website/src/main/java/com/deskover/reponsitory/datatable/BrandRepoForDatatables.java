package com.deskover.reponsitory.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Brand;

public interface BrandRepoForDatatables extends DataTablesRepository<Brand, Long> {
}
