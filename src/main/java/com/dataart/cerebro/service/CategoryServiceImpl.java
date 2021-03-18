package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Set<CategoryDTO> getAllCategory() {
        return categoryDAO.getAllCategory();
    }

    @Override
    public CategoryDTO getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }
}
