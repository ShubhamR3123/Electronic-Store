package com.electronic.store.services;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    //create Category

    CategoryDto createCategory(CategoryDto categoryDto);

    //update Category

    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    //get All Category
    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    //delete Category
    void deleteCategory(String categoryId);

    //get Category by id
    CategoryDto getCategoryById(String categoryId);

    //Search title

    List<CategoryDto> searchCategory(String title);
}
