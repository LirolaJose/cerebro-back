package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.CategoryRepository;
import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getAllCategory() {
        return categoryRepository.getAllCategories();
    }
}
