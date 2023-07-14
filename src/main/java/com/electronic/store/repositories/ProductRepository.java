package com.electronic.store.repositories;

import com.electronic.store.dtos.ProductDto;
import com.electronic.store.entites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByTitleContaining(String title, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);


}
