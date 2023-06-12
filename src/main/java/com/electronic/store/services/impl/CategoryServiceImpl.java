package com.electronic.store.services.impl;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.entites.Category;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.CategoryRepository;
import com.electronic.store.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    /**
     * @author Shubham Dhokchaule
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Initiated Dao Call For Create Category");
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        log.info("Completed Dao Call For Create Category");
        return modelMapper.map(saveCategory,CategoryDto.class);
    }

    /**
     * @author Shubham Dhokchaule
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Initiated Dao Call For update  Category with categoryId:{}",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        log.info("Completed Dao Call For Create Category with categoryId:{}",categoryId);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     */
    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        log.info("Initiated Dao call for get All  Users ");
        Page<Category> page = this.categoryRepository.findAll(pageable);
       return null;

    }

    /**
     * @author Shubham Dhokchaule
     * @param categoryId
     */
    @Override
    public void deleteCategory(String categoryId) {
        log.info("Initiated Dao call for Delete Category With categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        categoryRepository.delete(category);
        log.info("Completed Dao call for Delete Category With categoryId:{}", categoryId);

    }

    /**
     * @author Shubham Dhokchaule
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto getCategoryById(String categoryId) {
        log.info("Initiated Dao call for get Single Category With categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));

        return null;
    }

    /**
     * @author Shubham Dhokchaule
     * @param title
     * @return
     */
    @Override
    public List<CategoryDto> getCategoryByTitle(String title) {
        return null;
    }
}
