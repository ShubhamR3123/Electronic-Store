package com.electronic.store.controllers;

import com.electronic.store.dtos.*;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.services.CategoryService;
import com.electronic.store.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Value("${category.profile.image.path}")
    private String imageUplodPath;

    @Autowired
    private FileService fileService;

    @Autowired
    private CategoryService categoryService;


    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for Create Category
     * @param categoryDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Initiated Request for create Category");
        CategoryDto category = categoryService.createCategory(categoryDto);
        log.info("Completed Request for create Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote  THis Api is Used for Update Category
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        log.info("Initiated Request for update Category with categoryId:{}",categoryId);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        log.info("Completed Request for update Category with categoryId:{}",categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote  THis Api is Used for Get All Category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Initiated Request for getAll Category with pageNumber,pageSize:{}",pageNumber,pageSize);
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for getAll Category with pageNumber,pageSize:{}",pageNumber,pageSize);
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote  THis Api is Used for Delete Category
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        log.info("Initiated Request for delete Category with categoryId:{}",categoryId);
        this.categoryService.deleteCategory(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstants.CATEGORY_DELETED).status(HttpStatus.OK).success(true).build();
        log.info("Completed Request for delete Category with categoryId:{}",categoryId);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote  THis Api is Used for Get Single Category
     * @param categoryId
     * @return
     */

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>getCategoryById(@PathVariable String categoryId){
        log.info("Initiated Request for get single Category with categoryId:{}",categoryId);
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        log.info("Completed Request for get Single Category with categoryId:{}",categoryId);
        return  ResponseEntity.ok(categoryById);
    }
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uplodUserImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        log.info("Initiated request foruplod image details with image and userId:{}",image,categoryId);
        String imageName = fileService.uplodImage(image, imageUplodPath);
        CategoryDto category = categoryService.getCategoryById(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(category, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("Image Uplod Successfully..").status(HttpStatus.CREATED).build();
        log.info("Completed request for Uplod image details with image and userId:{}",image,categoryId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

}
