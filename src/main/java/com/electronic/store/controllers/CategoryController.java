package com.electronic.store.controllers;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/")
    public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
        log.info("Initiated Request for create Category");
        CategoryDto category = categoryService.createCategory(categoryDto);
        log.info("Completed Request for create Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto,categoryId);

        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }



}
