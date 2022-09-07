package com.deskover.service.impl;

import com.deskover.entity.FlashSale;
import com.deskover.entity.Product;
import com.deskover.reponsitory.FlashSaleRepository;
import com.deskover.reponsitory.datatable.FlashSaleRepoForDatatables;
import com.deskover.service.FlashSaleService;
import com.deskover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class FlashSaleServiceImpl implements FlashSaleService {

    @Autowired
    private FlashSaleRepository repository;

    @Autowired
    private FlashSaleRepoForDatatables repoForDatatables;

    @Autowired
    private ProductService productService;

    @Override
    public DataTablesOutput<FlashSale> getByActiveForDatatables(@Valid DataTablesInput input) {
        DataTablesOutput<FlashSale> discount = repoForDatatables.findAll(input);
        if (discount.getError() != null) {
            throw new IllegalArgumentException(discount.getError());
        }
        return discount;
    }

    public FlashSale changeActive(Long id) {


        FlashSale flashSale = repository.findById(id).orElse(null);
        if (flashSale != null) {
            if (flashSale.getActived()) {
                // Remove all product from flash sale
                flashSale.getProducts().forEach(product -> {
                    product.setFlashSale(null);
                    productService.save(product);
                });

                flashSale.setActived(false);
            } else {
                // Disable all other flash sale
                repository.findAllByActived(Boolean.TRUE).forEach(flashSaleItem -> {
                    // Remove all product from flash sale
                    flashSaleItem.getProducts().forEach(product -> {
                        product.setFlashSale(null);
                        productService.save(product);
                    });
                    flashSaleItem.setActived(false);
                    repository.save(flashSaleItem);
                });

                flashSale.setActived(true);
            }
            flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            FlashSale result = repository.saveAndFlush(flashSale);

            return result;
        } else {
            throw new IllegalArgumentException("Flash Sale not found");
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
        if (flashSale.getStartDate().getTime() < currentTime.getTime()
                || flashSale.getStartDate().getTime() >= flashSale.getEndDate().getTime()) {
            throw new IllegalArgumentException("Thời gian không hợp lệ");
        }
        flashSale.setActived(false);
        flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repository.save(flashSale);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FlashSale flashSale = repository.findById(id).orElse(null);
        if (flashSale == null) {
            throw new IllegalArgumentException("Flash Sale này không tồn tại");
        }

        Set<Product> products = flashSale.getProducts();
        for (Product product : products) {
            product.setFlashSale(null);
            productService.save(product);
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

    @Override
    public FlashSale activeToggle(Long id) {
        FlashSale flashSale = repository.findById(id).orElse(null);

        if (flashSale == null) {
            throw new IllegalArgumentException("Flash Sale not found");
        }

        flashSale.setActived(!flashSale.getActived());
        flashSale.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        repository.findAllByActived(Boolean.TRUE).forEach(flashSaleItem -> {
            if (flashSale.getActived()) {
                flashSaleItem.getProducts().forEach(product -> {
                    product.setFlashSale(null);
                    productService.save(product);
                });
            }

            flashSaleItem.setActived(false);
            repository.save(flashSaleItem);
        });

        return repository.saveAndFlush(flashSale);
    }
}
