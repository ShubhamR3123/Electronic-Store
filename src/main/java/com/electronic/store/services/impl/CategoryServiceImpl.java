package com.electronic.store.services.impl;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.Category;
import com.electronic.store.entites.User;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    /**
     * @author Shubham Dhokchaule
     * @apiNote This method is used for create Category
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Initiated Dao Call For Create Category");
        //create categoryID Randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(category);
        log.info("Completed Dao Call For Create Category");
        return modelMapper.map(saveCategory,CategoryDto.class);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This method is used for update Category
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Initiated Dao Call For update  Category with categoryId:{}",categoryId);
        Category category =this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = this.categoryRepository.save(category);
        log.info("Completed Dao Call For Create Category with categoryId:{}",categoryId);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }



    /**
     * @author Shubham Dhokchaule
     * @apiNote This api is used for getAll Category
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
        Page<Category> all = this.categoryRepository.findAll(pageable);
        Page<Category> page=all;
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
        return response;

    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Method is used for delete Category
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
     * @apiNote THis method is used for get Cateory By Id
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto getCategoryById(String categoryId) {
        log.info("Initiated Dao call for get Single Category With categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        log.info("Completed Dao call for get Single Category With categoryId:{}", categoryId);
        return modelMapper.map(category,CategoryDto.class);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Method Is used FOr Search category
     * @param title
     * @return
     */
    @Override
    public List<CategoryDto> searchCategory(String title) {
        log.info("Initiated Dao call for get Single Category With title:{}", title);
        List<Category> categoryList = this.categoryRepository.findByTitleContaining(title);
       // List<CategoryDto> collect = categoryList.stream().map(category -> entityToDto(category)).collect(Collectors.toList());
        log.info("Completed Dao call for get Single Category With title:{}", title);

        return null;
    }

    private Object entityToDto(Category category) {
        return modelMapper.map(category,CategoryDto.class);
    }
}
