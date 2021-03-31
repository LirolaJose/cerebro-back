package com.dataart.cerebro.service.serviceImpl;

import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.dto.CategoryDTO;
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
    public CategoryDTO getCategoryById(Integer categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }
}
