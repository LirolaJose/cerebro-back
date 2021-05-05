package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.domain.Type;
import com.dataart.cerebro.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getCategoryByType(@PathVariable Type type) {
        List<Category> categories = categoryService.findCategoriesByType(type);
        // FIXME: 5/5/2021 always return ok
        return categories != null && !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
