package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.repository.FlashSaleRepository;
import com.deskover.model.entity.database.repository.datatable.FlashSaleRepoForDatatables;
import com.deskover.service.FlashSaleService;
import com.deskover.service.ProductService;

@Service
public class FlashSaleServiceImpl implements FlashSaleService {

	@Autowired
	private FlashSaleRepository repository;

	@Autowired
	private FlashSaleRepoForDatatables repoForDatatables;

	@Autowired
	private ProductService productService;

	@Override
	public DataTablesOutput<FlashSale> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive) {
		DataTablesOutput<FlashSale> discount = repoForDatatables.findAll(input,
				(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
		if (discount.getError() != null) {
			throw new IllegalArgumentException(discount.getError());
		}
		return discount;
	}

	public FlashSale changeActive(Long id) {
		FlashSale flashSale = repository.findById(id).orElse(null);
		if (flashSale != null) {
			flashSale.setActived(!flashSale.getActived());
			flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			FlashSale result = repository.saveAndFlush(flashSale);

			// Remove the discount from the products
			Set<Product> products = result.getProducts();
			for (Product product : products) {
				product.setFlashSale(null);
			}

			return result;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void isCheckActived() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		List<FlashSale> listActived = repository.findAllByActived(Boolean.TRUE);
		listActived.forEach((item) -> {
			if (item.getEndDate().getTime() < currentTime.getTime()) {
				this.changeActive(item.getId());
				repository.saveAndFlush(item);
			}
		});
	}

	@Transactional
	@Override
	public FlashSale create(FlashSale flashSale) {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (flashSale.getStartDate().getDate() < currentTime.getDate()
				|| flashSale.getEndDate().getDate() < flashSale.getStartDate().getDate()) {
			throw new IllegalArgumentException("Ngày bắt đầu hoặc kết thúc không hợp lệ");
		} else if (flashSale.getStartDate().getDate() == currentTime.getDate()) {
			if (currentTime.getTime() > flashSale.getStartDate().getTime()
					|| flashSale.getStartDate().getTime() >= flashSale.getEndDate().getTime()) {
				throw new IllegalArgumentException("Thời gian không hợp lệ");
			} else {
				flashSale.setActived(false);
				flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				return repository.save(flashSale);
			}
		} else if (flashSale.getStartDate().getDate() > currentTime.getDate()) {
			if (flashSale.getStartDate().getTime() >= flashSale.getEndDate().getTime()) {
				throw new IllegalArgumentException("Thời gian kết thúc phải > thời gian bắt đầu");
			} else {
				flashSale.setActived(false);
				flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				return repository.save(flashSale);
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		FlashSale flashSale = repository.findById(id).orElse(null);
		if (flashSale == null) {
			throw new IllegalArgumentException("Flashsale này không tồn tại");
		}

		Set<Product> products = flashSale.getProducts();
		for (Product product : products) {
			product.setFlashSale(null);
		}

		repository.delete(flashSale);
	}

	@Override
	public FlashSale getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public FlashSale getFlashSale() {
		return repository.findFirstByActived(true);
	}

	@Override
	public FlashSale updateProductToFlashSale(FlashSale flashSale, Long productIdToAdd, Long productIdToRemove) {
		if (productIdToAdd != null) {
			Product product = productService.findById(productIdToAdd);
			product.setFlashSale(flashSale);
			if (productService.save(product) == null) {
				throw new IllegalArgumentException("Không thể cập nhật sản phẩm");
			}
		}

		if (productIdToRemove != null) {
			Product product = productService.findById(productIdToRemove);
			product.setFlashSale(null);
			if (productService.save(product) == null) {
				throw new IllegalArgumentException("Không thể cập nhật sản phẩm");
			}
		}

		flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return repository.saveAndFlush(flashSale);
	}

}
