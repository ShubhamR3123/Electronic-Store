package com.electronic.store.controllers;
import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.ImageResponse;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.services.FileService;
import com.electronic.store.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${product.profile.image.path}")
    private String imageUplodPath;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductService productService;

    @Autowired(required = false)
    private ProductDto productDto;
    /**
     * @param productDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This api is used for Create product
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("Initiated Request for create Product");
        ProductDto product = this.productService.createProduct(productDto);
        log.info("Completed Request for create Product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used for update Product
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        log.info("Initiated Request for update Product with productId And productDto:{}", productId, productDto);
        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        log.info("Completed Request for update Product with productId And productDto:{}", productId, productDto);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used for Delete Product
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
        log.info("Initiated Request for Delete Product with productId :{}", productId);
        this.productService.deleteProduct(productId);
        ApiResponseMessage response = ApiResponseMessage.builder().message(AppConstants.PRODUCT_DELETED).success(true).status(HttpStatus.OK).build();
        log.info("Completed Request for Delete Product with productId:{}", productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    /**
     * @param productId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is Used for get Single Product
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        log.info("Initiated Request for Single Product with productId :{}", productId);
        ProductDto singleProduct = this.productService.getSingleProduct(productId);
        log.info("Initiated Request for Single Product with productId :{}", productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     * @apiNote this Api is Used for get All Products
     */
    @GetMapping
    @Scheduled(cron = "0/15 * * * * *")
    public ResponseEntity<PageableResponse> getAllProduct(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("Initiated Request for Get All Products with pageNumber,pageSize,sortBy,sortDir :{}", pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<ProductDto> allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Get All Products with pageNumber,pageSize,sortBy,sortDir :{}", pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used For get All Live Products
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse> getAllLive(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("Initiated Request for Get AllLive Products with pageNumber,pageSize,sortBy,sortDir :{}", pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<ProductDto> allProduct = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Get AllLive Products with pageNumber,pageSize,sortBy,sortDir :{}", pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is Used For Search Product
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse> serarchProduct(@PathVariable String query, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("Initiated Request for Search Products with pageNumber,pageSize,sortBy,sortDir :{}", pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<ProductDto> allProduct = this.productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Search  Products with pageNumber,pageSize,sortBy,sortDir :{}", pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }
//uplod image

    /**
     * @param image
     * @param productId
     * @return
     * @throws IOException
     * @author Shubham Dhokchaule
     * @apiNote This Api is Used For uplod Product Image
     */
    @PostMapping("image/{productId}")
    public ResponseEntity<ImageResponse> uplodProductImage(@RequestPart("prodImage") MultipartFile image, @PathVariable String productId) throws IOException {
        log.info("Initiated request foruplod image details with image and productId:{}", image, productId);
        String uplodImage = fileService.uplodImage(image, imageUplodPath);
        ProductDto product = productService.getSingleProduct(productId);
        product.setImageName(uplodImage);
        ProductDto productDto = productService.updateProduct(product, productId);
        ImageResponse response = ImageResponse.builder().imageName(uplodImage).success(true).message(AppConstants.PIMAGE_UPLOD).status(HttpStatus.OK).build();
        log.info("Completed request foruplod image details with image and productId:{}", image, productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param productId
     * @param response
     * @throws IOException
     * @author Shubham Dhokchaule
     * @apiNote This Api is Used For serve Product Image
     */
    @GetMapping("image/{productId}")
    public void serveImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("Initiated request serve image details with  productId:{}", productId);
        ProductDto product = productService.getSingleProduct(productId);
        InputStream resource = fileService.getResource(imageUplodPath, product.getImageName());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed request serve image details with  productId:{}", productId);
    }

    @PostMapping("/category/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategoryId(@Valid @RequestBody ProductDto productDto, @PathVariable String categoryId) {
        log.info("Request started for service layer to create product with categoryId :{}", categoryId);
        ProductDto product = this.productService.createWithCategory(productDto, categoryId);
        log.info("Request completed for service layer to create product with categoryId :{}", categoryId);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }
    @PutMapping("/category/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateProductWithCategoryId(@PathVariable String categoryId, @PathVariable String productId) {
        log.info("Request started for service layer to update product with categoryId :{}", categoryId + " and with productId :{}" + productId);
        ProductDto productDto = this.productService.updateProductWithCategory(categoryId, productId);
        log.info("Request completed for service layer to update product with categoryId :{}", categoryId + " and with productId :{}" + productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }
}