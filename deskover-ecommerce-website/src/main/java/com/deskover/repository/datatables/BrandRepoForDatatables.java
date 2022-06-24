package com.deskover.repository.datatables;

import com.deskover.entity.Brand;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface BrandRepoForDatatables extends DataTablesRepository<Brand, Long> {
}
