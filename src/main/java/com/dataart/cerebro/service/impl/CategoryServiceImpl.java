package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.domain.CategoryDTO;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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
}
