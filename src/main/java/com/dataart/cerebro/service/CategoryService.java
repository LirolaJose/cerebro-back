package com.dataart.cerebro.service;

import com.dataart.cerebro.repository.CategoryRepository;
import com.dataart.cerebro.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<Category> getAllCategory() {
        return categoryRepository.getAllCategories();
    }
}
