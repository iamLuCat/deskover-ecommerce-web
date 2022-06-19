package com.deskover.service.impl;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repo;

    /**
     * Find categories by activated
     *
     * @param isActive - true or false
     * @return list of categories by activated
     */
    @Override
    public List<Category> getByActived(Boolean isActive) {
        return repo.findByActived(isActive);
    }

    @Override
    public Page<Category> getByActived(Boolean isActive, Integer page, Integer size) {
        return repo.findByActived(isActive, PageRequest.of(page, size));
    }

    /**
     * Find category by id
     *
     * @param id - category id
     * @return category by id
     */
    @Override
    public Category getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Update category
     *
     * @param category - category to update
     * @return category updated
     * @throws SQLException - if error occurs
     */
    @Override
    @Transactional
    public Category update(Category category) throws SQLException {
        return repo.saveAndFlush(category);
    }

}
