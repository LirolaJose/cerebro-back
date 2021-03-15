package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.CategoryDTO;

public interface CategoryDAO {
    CategoryDTO getCategoryDTObyId(int categoryId);
}
