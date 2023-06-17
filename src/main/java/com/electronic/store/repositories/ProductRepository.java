package com.electronic.store.repositories;

import com.electronic.store.dtos.ProductDto;
import com.electronic.store.entites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product>findByTitleContaining(String title,Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);


}
