package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Category getCategoryById(int categoryId);

    @Query(nativeQuery = true, value = "SELECT 1")
    Set<Category> getAllCategories();
}
