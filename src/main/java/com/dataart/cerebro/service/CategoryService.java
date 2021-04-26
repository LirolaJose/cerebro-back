package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.domain.Type;
import com.dataart.cerebro.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findCategoriesByType(Type type){
        return categoryRepository.findCategoriesByTypeId(type.getId());
    }
    public Category findCategoryById(Long id){
        return categoryRepository.findCategoryById(id);
    }
}
