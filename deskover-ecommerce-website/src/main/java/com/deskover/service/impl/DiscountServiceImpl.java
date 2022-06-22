package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Discount;
import com.deskover.repository.DiscountRepository;
import com.deskover.repository.datatables.DiscountRepoForDatatables;
import com.deskover.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	private DiscountRepository repository;
	
	@Autowired
	private DiscountRepoForDatatables repoForDatatables;

	@Override
	public List<Discount> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public Discount create(Discount discount) {
		discount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		return repository.saveAndFlush(discount);
	}
	
	@Override
	@Transactional
	public Discount changeActive(Long id) {
		Discount discount = this.getById(id);
		if (discount!=null) {
			if(discount.getActived()) {
				discount.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				discount.setActived(Boolean.FALSE);
				return repository.saveAndFlush(discount);
			}else {
				discount.setDeletedDate(new Timestamp(System.currentTimeMillis()));
				discount.setActived(Boolean.TRUE);
				return repository.saveAndFlush(discount);
			}
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public Discount update(Discount discount) {
		discount.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		return repository.saveAndFlush(discount);
	}

	@Override
	public Discount findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Discount> optional = repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public DataTablesOutput<Discount> getAllForDatatables(DataTablesInput input) {
        DataTablesOutput<Discount> Discount = repoForDatatables.findAll(input);
        if (Discount.getError() != null) {
            throw new IllegalArgumentException(Discount.getError());
        }
        return Discount;
	}
	public Discount getById(Long id) { 
		return repository.findById(id).orElse(null);
	}


	

}
