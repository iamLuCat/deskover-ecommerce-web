package com.deskover.repository.datatables;

import com.deskover.entity.Category;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface CategoryRepoForDatatables extends DataTablesRepository<Category, Long> {
}
