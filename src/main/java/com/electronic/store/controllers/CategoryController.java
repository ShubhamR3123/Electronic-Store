package com.electronic.store.controllers;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.ImageResponse;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.services.CategoryService;
import com.electronic.store.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.Transient;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
     * @param categoryDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for Create Category
     */

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Initiated Request for create Category");
        CategoryDto category = categoryService.createCategory(categoryDto);
        log.info("Completed Request for create Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for Update Category
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        log.info("Initiated Request for update Category with categoryId:{}", categoryId);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        log.info("Completed Request for update Category with categoryId:{}", categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for Get All Category
     */
    @GetMapping("/category")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Initiated Request for getAll Category with pageNumber,pageSize:{}", pageNumber, pageSize);
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for getAll Category with pageNumber,pageSize:{}", pageNumber, pageSize);
        return new ResponseEntity<>(categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for Delete Category
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        log.info("Initiated Request for delete Category with categoryId:{}", categoryId);
        this.categoryService.deleteCategory(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstants.CATEGORY_DELETED).status(HttpStatus.OK).success(true).build();
        log.info("Completed Request for delete Category with categoryId:{}", categoryId);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for Get Single Category
     */

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        log.info("Initiated Request for get single Category with categoryId:{}", categoryId);
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        log.info("Completed Request for get Single Category with categoryId:{}", categoryId);
        return ResponseEntity.ok(categoryById);
    }

    /**
     * @param title
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used for serve CoverImage
     */


    @GetMapping("/search/{title}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable("title") String title) {
        log.info("Initiated request search category with title:{}", title);
        List<CategoryDto> searchCategory = this.categoryService.searchCategory(title);
        log.info("Completed request search category with title:{}", title);
        return new ResponseEntity<>(searchCategory, HttpStatus.OK);
    }

    /**
     * @param image
     * @param categoryId
     * @return
     * @throws IOException
     * @author Shubham Dhokchaule
     * @apiNote This api is used for uplod coverImage
     */
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uplodCategoryImage(@RequestPart("catImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        log.info("Initiated request foruplod image details with image and userId:{}", image, categoryId);
        String imageName = fileService.uplodFile(image, imageUplodPath);
        CategoryDto category = categoryService.getCategoryById(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(category, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("Image Uplod Successfully..").status(HttpStatus.CREATED).build();
        log.info("Completed request for Uplod image details with image and userId:{}", image, categoryId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }


    /**
     * @param categoryId
     * @param response
     * @throws IOException
     * @author Shubham Dhokchaule
     * @apiNote This Api is used for serve CoverImage
     */
    @GetMapping("image/{categoryId}")
    public void serveImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        log.info("Initiated request search category with categoryId:{}", categoryId);
        CategoryDto category = categoryService.getCategoryById(categoryId);
        InputStream resource = fileService.getResource(imageUplodPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed request search category with categoryId:{}", categoryId);
    }

}
