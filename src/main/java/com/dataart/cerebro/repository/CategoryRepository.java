package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value =
            "SELECT * FROM category c " +
            "join category_of_types cof on c.id = cof.category_id\n" +
            "join type t on t.id = cof.type_id " +
            "WHERE t.id = :typeId ;",
            nativeQuery = true)
    List<Category> findCategoriesByTypeId(@Param("typeId") Long typeId);

    Category findCategoryById(Long categoryId);
}
