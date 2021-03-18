package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.CategoryDTO;

import java.util.Set;

public interface CategoryService {
    Set<CategoryDTO> getAllCategory ();
    CategoryDTO getCategoryById(int categoryId);
}
