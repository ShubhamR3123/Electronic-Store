package com.electronic.store.services.impl;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.entites.Category;
import com.electronic.store.entites.Product;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.CategoryRepository;
import com.electronic.store.repositories.ProductRepository;
import com.electronic.store.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Value("${product.profile.image.path}")
    private String imageUplodPath;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
private CategoryRepository categoryRepository;
    /**
     * @param productDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Initiated Dao call for Save//Create Product");
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        productDto.setAddedDate(new Date());
        Product product = modelMapper.map(productDto, Product.class);
        Product saveProduct = this.productRepository.save(product);
        log.info("Completed Dao call for Save//Create Product");
        return modelMapper.map(saveProduct, ProductDto.class);
    }

    /**
     * @param productDto
     * @param productId
     * @return
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        log.info("Initiated Dao call for Update Product with productId:{}", productId);
        Product updateProduct = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + productId));
        updateProduct.setTitle(productDto.getTitle());
        updateProduct.setDescription(productDto.getDescription());
        updateProduct.setQuantity(productDto.getQuantity());
        updateProduct.setPrice(productDto.getPrice());
        updateProduct.setDiscounted_price(productDto.getDiscounted_price());
        // updateProduct.setAddedDate(productDto.getAddedDate());
        updateProduct.setStock(productDto.isStock());
        updateProduct.setLive(productDto.isLive());
        Product product = this.productRepository.save(updateProduct);
        log.info("Completed Dao call for Update Product with productId:{}", productId);
        return modelMapper.map(product, ProductDto.class);
    }

    /**
     * @param productId
     */
    @Override
    public void deleteProduct(String productId) {
        log.info("Initiated Dao call for Delete Product with productId:{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + productId));

        String fullPath = imageUplodPath + product.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException e) {
            log.error("image not found in folder:{}", e.getMessage());
        } catch (IOException e) {
            log.error("unale to found image:{}", e.getMessage());
        }
        this.productRepository.delete(product);
        log.info("Completed Dao call for delete Product with productId:{}", productId);


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
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiated Dao call for Get All Product with pageSize,pageNumber,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findAll(pageable);
        Page<Product> page = all;
        log.info("completed Dao call for Get All Product with pageSize,pageNumber,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public ProductDto getSingleProduct(String productId) {
        log.info("Initiated Dao call for Get Single Product with productId:{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + productId));
        log.info("Initiated Dao call for Get Single Product with productId:{}", productId);
        return modelMapper.map(product, ProductDto.class);
    }

    /**
     * @return
     */
    @Override
    public PageableResponse getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiated Dao call for Get All Product with pageSize,pageNumber,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findByLiveTrue(pageable);
        Page<Product> page = all;
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    /**
     * @param title
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    public PageableResponse<ProductDto> searchByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiated Dao call for Serach  Product with title:{}", title);
        log.info("Initiated Dao call for Get All Product with pageSize,pageNumber,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findByTitleContaining(title, pageable);
        Page<Product> page = all;
        log.info("Completed Dao call for Serach  Product with title:{}", title);
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    /**
     * @param productDto
     * @param categoryId
     * @return
     */
    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        log.info("Request initiate of the dao call for save the product data with categoryId:{}",categoryId);
        //fetch the category from DB:
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        Product products = this.modelMapper.map(productDto, Product.class);
        String randomId = UUID.randomUUID().toString();
        products.setProductId(randomId);
        products.setAddedDate(new Date());
        products.setCategory(category);
        Product save = this.productRepository.save(products);
        log.info("Request Completed of the dao call for save the product data with categoryId:{}",categoryId);
        return this.modelMapper.map(save, ProductDto.class);
    }

    /**
     * @param productId
     * @param categoryId
     * @return
     */
    @Override
    public ProductDto updateProductWithCategory(String productId, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId));
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + productId));
        product.setCategory(category);
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(save,ProductDto.class);
    }
}
