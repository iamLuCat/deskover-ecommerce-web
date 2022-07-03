package com.deskover.service.impl;

import com.deskover.entity.Discount;
import com.deskover.repository.DiscountRepository;
import com.deskover.repository.datatables.DiscountRepoForDatatables;
import com.deskover.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	private DiscountRepository repository;
	
	@Autowired
	private DiscountRepoForDatatables repoForDatatables;
	
	public Discount getById(Long id) { 
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Discount> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public Discount create(Discount discount) {
		discount.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		discount.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return repository.saveAndFlush(discount);
	}
	
	@Override
	@Transactional
	public Discount changeActive(Long id) {
		Discount discount = this.getById(id);
		if (discount!=null) {
			if(discount.getActived()) {
				discount.setModifiedAt(new Timestamp(System.currentTimeMillis()));
				discount.setActived(Boolean.FALSE);
				discount.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				return repository.saveAndFlush(discount);
			}else {
				discount.setModifiedAt(new Timestamp(System.currentTimeMillis()));
				discount.setActived(Boolean.TRUE);
				discount.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				return repository.saveAndFlush(discount);
			}
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public Discount update(Discount discount) {
		discount.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		discount.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return repository.saveAndFlush(discount);
	}

	@Override
	public Discount findById(Long id) {
		Optional<Discount> optional = repository.findById(id);
		return optional.orElse(null);
	}

	@Override
	public DataTablesOutput<Discount> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive) {
		DataTablesOutput<Discount> discount = repoForDatatables.findAll(input,(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
		if (discount.getError() != null) {
			throw new IllegalArgumentException(discount.getError());
		}
		return discount;
	}


	

}
