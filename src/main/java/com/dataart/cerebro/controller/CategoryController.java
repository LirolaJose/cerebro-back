package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.domain.Type;
import com.dataart.cerebro.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Api(tags = "Category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{type}")
    public ResponseEntity<?> getCategoryByType(@PathVariable Type type) {
        List<Category> categories = categoryService.findCategoriesByType(type);
        return ResponseEntity.ok(categories);
    }
}