package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.CategoryDTO;

import java.util.Set;

public interface CategoryService {
    Set<CategoryDTO> getAllCategory ();
    CategoryDTO getCategoryById(Integer categoryId);
}
