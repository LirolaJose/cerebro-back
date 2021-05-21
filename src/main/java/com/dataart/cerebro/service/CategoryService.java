package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.domain.Type;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findCategoriesByType(Type type) {
        return categoryRepository.findCategoriesByTypeId(type.getId());
    }

    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category", categoryId));
    }
}
