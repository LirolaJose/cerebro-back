package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
