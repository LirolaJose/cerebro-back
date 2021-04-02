package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.CategoryDTO;

import java.util.Set;

public interface CategoryDAO {
    CategoryDTO getCategoryById(int categoryId);

    Set<CategoryDTO> getAllCategories();
}
