package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;

public interface ProductService {

    //Create Product
    ProductDto createProduct(ProductDto productDto);

    //Update Product
    ProductDto updateProduct(ProductDto productDto, String productId);

    //Delete Product

    void deleteProduct(String productId);

    //Get All Products
    PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Get Single Product
    ProductDto getSingleProduct(String productId);

    //get all Live
    PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //
    PageableResponse<ProductDto> searchByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
