//package com.dataart.cerebro.controller.controllers;
//
//import com.dataart.cerebro.service.CategoryService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@Slf4j
//public class CategoryController {
//    private final CategoryService categoryService;
//
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    @RequestMapping("/categories")
//    public String getCategoryList(Model model) {
//        model.addAttribute("categorySet", categoryService.getAllCategory());
//        return "categorySet";
//    }
//}
