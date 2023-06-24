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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Value("${category.profile.image.path}")
    private String imageUplodPath;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param categoryDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for create Category
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
        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for update Category
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Initiated Dao Call For update  Category with categoryId:{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = this.categoryRepository.save(category);
        log.info("Completed Dao Call For Create Category with categoryId:{}", categoryId);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }


    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This api is used for getAll Category
     * @author Shubham Dhokchaule
     */
    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        log.info("Initiated Dao call for get All  Users with pageNumber,pageSize,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        Page<Category> all = this.categoryRepository.findAll(pageable);
        Page<Category> page = all;
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("Completed Dao call for get All  Users with pageNumber,pageSize,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        return response;

    }

    /**
     * @param categoryId
     * @author Shubham Dhokchaule
     * @apiNote This Method is used for delete Category
     */

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Initiated Dao call for Delete Category With categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));

        String fullPath = imageUplodPath + category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException e) {
            log.error("image not found in folder:{}", e.getMessage());
        } catch (IOException e) {
            log.error("unale to found image:{}", e.getMessage());
        }
        categoryRepository.delete(category);
        log.info("Completed Dao call for Delete Category With categoryId:{}", categoryId);

    }

    /**
     * @param categoryId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis method is used for get Cateory By Id
     */
    @Override
    public CategoryDto getCategoryById(String categoryId) {
        log.info("Initiated Dao call for get Single Category With categoryId:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        log.info("Completed Dao call for get Single Category With categoryId:{}", categoryId);
        return modelMapper.map(category, CategoryDto.class);
    }

    /**
     * @param title
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Method Is used FOr Search category
     */
    @Override
    public List<CategoryDto> searchCategory(String title) {
        log.info("Initiated Dao call for get Single Category With title:{}", title);
        List<Category> categoryList = this.categoryRepository.findByTitleContaining(title);
        List<CategoryDto> collect = categoryList.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        log.info("Completed Dao call for get Single Category With title:{}", title);
        return collect;
    }

    private Object entityToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
