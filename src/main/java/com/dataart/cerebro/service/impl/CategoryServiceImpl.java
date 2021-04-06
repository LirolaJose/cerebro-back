package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.domain.CategoryDTO;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Set<CategoryDTO> getAllCategory() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        CategoryDTO category = categoryDAO.getCategoryById(id);
        if (category == null) {
            log.info("Bad request for ID({}), this id doesn't exist", id);
            throw new NotFoundException("Category", id);
        }
        return category;
    }
}
