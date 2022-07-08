package com.deskover.service.impl;

import com.deskover.entity.Discount;
import com.deskover.entity.Product;
import com.deskover.repository.DiscountRepository;
import com.deskover.repository.datatables.DiscountRepoForDatatables;
import com.deskover.service.DiscountService;
import com.deskover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository repository;

    @Autowired
    private DiscountRepoForDatatables repoForDatatables;

    @Autowired
    ProductService productService;

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
        discount.setActived(Boolean.TRUE);
        discount.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        discount.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repository.saveAndFlush(discount);
    }

    @Override
    @Transactional
    public Discount changeActive(Long id) {
        Discount discount = this.getById(id);
        if (discount != null) {
            discount.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            discount.setActived(!discount.getActived());
            discount.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            Discount result = repository.saveAndFlush(discount);

            // Remove the discount from the products
            Set<Product> products = result.getProducts();
            for (Product product : products) {
                product.setDiscount(null);
            }

            return result;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Discount update(Discount discount, Long productIdToAdd, Long productIdToRemove) {
        if (productIdToAdd != null) {
            Product product = productService.findById(productIdToAdd);
            product.setDiscount(discount);
            if (productService.update(product) == null) {
                throw new IllegalArgumentException("Không thể cập nhật sản phẩm");
            }
        }

        if (productIdToRemove != null) {
            Product product = productService.findById(productIdToRemove);
            product.setDiscount(null);
            if (productService.update(product) == null) {
                throw new IllegalArgumentException("Không thể cập nhật sản phẩm");
            }
        }

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
        DataTablesOutput<Discount> discount = repoForDatatables.findAll(input,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
        if (discount.getError() != null) {
            throw new IllegalArgumentException(discount.getError());
        }
        return discount;
    }

}
